package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.ui.MenuAdapter
import com.anggarad.dev.foodfinder.databinding.FragmentDetailsRestoBinding

class DetailsRestoFragment : Fragment() {

    private lateinit var binding: FragmentDetailsRestoBinding
    private var detailResto: RestoDetail? = null

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

        val menuAdapter = MenuAdapter()

        detailResto = arguments?.getParcelable(DetailsActivity.EXTRA_DATA)

        attachData(detailResto)


        with(binding.rvMenuPhoto) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = menuAdapter
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

        }
    }

}