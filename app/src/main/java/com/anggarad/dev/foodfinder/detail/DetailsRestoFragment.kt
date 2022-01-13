package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.ui.MenuAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentDetailsRestoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsRestoFragment : Fragment() {

    private lateinit var binding: FragmentDetailsRestoBinding
    private var detailResto: RestoDetail? = null
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsRestoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuAdapter = MenuAdapter()
        with(binding.rvMenu) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = menuAdapter
        }


        if (activity != null) {
            detailResto = arguments?.getParcelable(DetailsActivity.EXTRA_DATA)

            attachData(detailResto)


        }


    }

    private fun attachData(detailResto: RestoDetail?) {

        detailResto?.let {
            with(binding) {
                tvOperationalHours.text = it.opHours
                if (it.isHalal != 1) {
                    tvHalalDetail.visibility = View.GONE
                } else {
                    tvHalalDetail.visibility = View.VISIBLE
                    tvHalalDetail.text = "Halal"
                }
                tvLocationDetail.text = it.address
                tvRestoPhone.text = it.contacts
            }

            val id = it.restoId

            if (id != null) {
                detailViewModel.getMenu(id).observe(viewLifecycleOwner, { menuList ->
                    if (menuList != null) {
                        when (menuList) {
                            is Resource.Success -> {
                                if (menuList.data?.isEmpty() == true) {
                                    binding.progressBar.visibility = View.GONE
                                    binding.viewNoMenu.root.visibility = View.VISIBLE
                                }

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

                })

            }


        }
    }

}