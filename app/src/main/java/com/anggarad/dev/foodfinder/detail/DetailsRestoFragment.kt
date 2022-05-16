package com.anggarad.dev.foodfinder.detail

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.ui.MenuAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentDetailsRestoBinding
import com.anggarad.dev.foodfinder.reviews.ReviewsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

private const val ARG_DETAIL = "param1"
private const val ARG_LOCATION = "param2"

class DetailsRestoFragment : Fragment() {

    companion object {
        fun newInstance(detailResto: RestoDetail, param2: Location): DetailsRestoFragment {
            val frag = DetailsRestoFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_DETAIL, detailResto)
            bundle.putParcelable(ARG_LOCATION, param2)
            frag.arguments = bundle
            return frag
        }
    }

    private lateinit var binding: FragmentDetailsRestoBinding
    private var detailResto: RestoDetail? = null
    private var location: Location? = null
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsRestoBinding.inflate(inflater, container, false)
        arguments?.let {
            detailResto = it.getParcelable(ARG_DETAIL)
            location = it.getParcelable(ARG_LOCATION)

//            location?.let { loc ->
//                latitude = loc.latitude
//                longitude = loc.longitude
//            }
        }
        bundle = Bundle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuAdapter = MenuAdapter()
        with(binding.rvMenu) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = menuAdapter
        }


        if (activity != null) {
            attachData(detailResto)
            onMenuClick()
            onReviewClick()
        }
        binding.btnShowMap.root.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${detailResto?.latitude},${detailResto?.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun attachData(detailResto: RestoDetail?) {
        detailResto?.let {
            binding.tvRestoTitle.text = it.name
            binding.tvDetailCategory.text =
                it.categories.toString().replace("[", "").replace("]", "")
            binding.btnOpenReview.ratingText.text = it.ratingAvg.toString()
            binding.btnOpenHours.openHours.text = it.opHours
            binding.tvRestoPhone.text = it.contacts
            binding.tvLocationDetail.text = it.address

            val restoPhoneNum = it.contacts
            binding.tvRestoPhone.setOnClickListener {
                val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$restoPhoneNum"))
                startActivity(intentDial)
            }
            calculateDistance(it.latitude, it.longitude)



            when {
                it.haveInternet != 1 -> {
                    binding.tvFreeWifi.visibility = View.GONE
                }
                it.haveToilet != 1 -> {
                    binding.tvToilets.visibility = View.GONE
                }
                it.haveSocket != 1 -> {
                    binding.tvPowerOutlet.visibility = View.GONE
                }
                it.haveMusholla != 1 -> {
                    binding.tvMushola.visibility = View.GONE
                }
                it.isHalal != 1 -> {
                    binding.btnOpenHalal.root.visibility = View.GONE
                }
                else -> {
                    binding.tvFreeWifi.visibility = View.VISIBLE
                    binding.tvToilets.visibility = View.VISIBLE
                    binding.tvPowerOutlet.visibility = View.VISIBLE
                    binding.tvMushola.visibility = View.VISIBLE
                    binding.btnOpenHalal.root.visibility = View.VISIBLE
                    binding.dividerHalalMap.visibility = View.VISIBLE

                }
            }

            val id = it.restoId
            if (id != null) {
                detailViewModel.getMenu(id).observe(viewLifecycleOwner) { menuList ->
                    if (menuList != null) {
                        when (menuList) {
                            is Resource.Success -> {
                                if (menuList.data?.isEmpty() == true) {
                                    binding.viewNoMenu.root.visibility = View.VISIBLE
                                }
                                binding.progressBar.visibility = View.GONE
                                menuAdapter.setMenuList(menuList.data)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.viewNoMenu.root.visibility = View.VISIBLE
                            }
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                        }
                    } else {
                        binding.viewNoMenu.root.visibility = View.VISIBLE
                    }

                }

            }
        }
    }

    private fun calculateDistance(restoLat: Double, restoLng: Double) {
        val userLocation = Location("userLocation")
        userLocation.longitude = location?.longitude ?: 0.0
        userLocation.latitude = location?.latitude ?: 0.0
        val restoLocation = Location("restoLocation")
        restoLocation.latitude = restoLat
        restoLocation.longitude = restoLng
        val distance = userLocation.distanceTo(restoLocation)
        val distanceRes = (distance / 1000 * 10.0).roundToInt() / 10.0f
        binding.btnShowMap.location.text = "$distanceRes Km"
    }

    private fun onReviewClick() {
        binding.btnOpenReview.root.setOnClickListener {
            val intent = Intent(activity, ReviewsActivity::class.java)
            intent.putExtra(ReviewsActivity.EXTRA_DATA, detailResto)
            startActivity(intent)
//            val reviewFragment = detailResto?.let { ReviewFragment.newInstance(it) }
//            reviewFragment.let { it?.let { it1 ->
//                parentFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_OPEN).replace(R.id.frg,
//                    it1).commit()
//            } }
        }
    }


    private fun onMenuClick() {
        menuAdapter.onItemClick = { selectedItem ->

            bundle.putParcelable(MenuDetailFragment.MENU_DATA, selectedItem)
            val menuDetailFragment = MenuDetailFragment()
            menuDetailFragment.arguments = bundle
            menuDetailFragment.show(parentFragmentManager, "ModalBottomFragment")
        }
    }

}