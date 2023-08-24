package com.ryz.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ryz.myapplication.MainActivity
import com.ryz.myapplication.R
import com.ryz.myapplication.common.customToolbar
import com.ryz.myapplication.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpMenu()
    }

    private fun setUpToolbar() {
        binding.layoutToolbars.topAppBar.customToolbar(
            activity as MainActivity,
            title = getString(R.string.product_list_title),
            isShowMenu = true,
            menuId = R.menu.add_product
        )
    }

    private fun setUpMenu() {
        binding.layoutToolbars.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_product -> {
                    findNavController().navigate(R.id.action_productListFragment_to_productInputFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}