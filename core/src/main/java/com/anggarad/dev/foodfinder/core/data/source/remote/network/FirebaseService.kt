package com.anggarad.dev.foodfinder.core.data.source.remote.network

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.model.UserRegister
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FirebaseService {

    private val fDb: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun uploadImage(uri: Uri, uid: String, type: String, name: String): LiveData<Resource<String>> {
        val mStorage: FirebaseStorage = Firebase.storage
        val storageRef = mStorage.reference
        val fileRef = storageRef.child("$uid/$type/$name")
        val downloadUrl = MutableLiveData<Resource<String>>()

        fileRef.putFile(uri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                    downloadUrl.postValue(Resource.Error(it.toString()))
                }
            }
            fileRef.downloadUrl
        }.addOnCompleteListener { task ->
            downloadUrl.postValue(Resource.Loading())
            if (task.isSuccessful) {
                val downloadUri = task.result
                downloadUrl.postValue(Resource.Success(downloadUri.toString()))
                Log.d("uploadFile: ", downloadUri.toString())
            } else {
                task.exception?.let {
                    throw it
                    downloadUrl.postValue(Resource.Error(it.toString()))
                }
            }
        }
        return downloadUrl
    }

    fun getUserDetail(userId: String): LiveData<Resource<UserDetail>> {
        val userRef = fDb.getReference("Users")
        val userData = MutableLiveData<Resource<UserDetail>>()

        CoroutineScope(IO).launch {
            userData.postValue(Resource.Loading())
            val userListner = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.child(userId).getValue<UserDetail>()
                    userData.postValue(Resource.Success(user!!))
                }

                override fun onCancelled(error: DatabaseError) {
                    userData.postValue(Resource.Error(error.toException().toString()))
                }
            }

            userRef.addValueEventListener(userListner)
        }
        return userData
    }

    fun loginWithEmailFb(email: String, password: String): LiveData<Resource<UserRegister>> {
        val currentUserData = MutableLiveData<Resource<UserRegister>>()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            currentUserData.postValue(Resource.Loading())
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    fDb.getReference("Users").child(it.uid)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val user = snapshot.getValue<UserRegister>()
                                currentUserData.postValue(Resource.Success(user!!))
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.w("LoginFb", "loadPost:onCancelled", error.toException())
                                currentUserData.postValue(Resource.Error(error.toString()))
                            }

                        })
                }
            } else {
                Log.d("Error LoginFb ", task.exception?.message.toString())
                currentUserData.postValue(Resource.Error(task.exception?.message.toString()))
            }
        }
        return currentUserData
    }

    fun registerWithEmailFb(
        email: String,
        password: String,
        userRegisterModel: UserRegister
    ): LiveData<Resource<UserRegister>> {
        val createdUserData = MutableLiveData<Resource<UserRegister>>()

        CoroutineScope(IO).launch {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                createdUserData.postValue(Resource.Loading())
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        fDb.getReference("Users")
                            .child(it.uid)
                            .setValue(userRegisterModel)
                        createdUserData.postValue(Resource.Success(userRegisterModel))
                    }
                } else {
                    Log.d("Error CreateUser ", task.exception?.message.toString())
                    createdUserData.postValue(Resource.Error(task.exception?.message.toString()))
                }
            }
        }


        return createdUserData
    }

    fun continueWithGoogle(idToken: String): LiveData<Resource<UserRegister>> {
        val authenticatedUser = MutableLiveData<Resource<UserRegister>>()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        CoroutineScope(IO).launch {
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val isNew = task.result?.additionalUserInfo?.isNewUser
                    val user = auth.currentUser
//                    if (user != null){
//                        val userInfo = UserRegister(
//                            fullName = user.displayName,
//                            email = user.email,
//                            imgProfile = user.photoUrl.toString(),
//                            phoneNum = user.phoneNumber
//                        )
//                        if (isNew == true){
//                            fDb.getReference("Users")
//                                .child(user.uid)
//                                .setValue(userInfo)
//                        }
//                    }

                }
            }
        }

        return authenticatedUser
    }
}