package com.anggarad.dev.foodfinder.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.anggarad.dev.foodfinder.core.ui.FavoriteAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentFavoriteBinding
import com.anggarad.dev.foodfinder.detail.DetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
//        val supportActionBar = Action
//        supportActionBar?.title = "Favorite"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val favoriteAdapter = FavoriteAdapter()
        favoriteAdapter.onItemClick = { selectedItem ->
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.RESTO_ID, selectedItem.restoId)
            startActivity(intent)
        }

        favoriteViewModel.favoriteResto.observe(viewLifecycleOwner, { favList ->
            favoriteAdapter.setFavoriteList(favList)
            binding.viewEmpty.root.visibility =
                if (favList.isNotEmpty()) View.GONE else View.VISIBLE
        })

        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

}