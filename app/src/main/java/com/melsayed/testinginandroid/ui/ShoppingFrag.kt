package com.melsayed.testinginandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.melsayed.testinginandroid.adapters.ShoppingItemsListAdapter
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.databinding.FragmentShoppingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFrag : Fragment(),ShoppingItemsListAdapter.OnShoppingItemClicked {
    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel:ShoppingViewModel
//    private val viewModel by activityViewModels<ShoppingViewModel>()
    @Inject
    lateinit var glide:RequestManager
    lateinit var shoppingAdapter: ShoppingItemsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        shoppingAdapter = ShoppingItemsListAdapter(glide,this)
        binding.apply {
            fbAddItem.setOnClickListener {
                val action = ShoppingFragDirections.actionShoppingFragToAddShoppingItemFrag()
                findNavController().navigate(action)
            }
            viewModel.totalPrice.observe(viewLifecycleOwner) {
                txtTotalCost.text = String.format("%s%.1f%s", "Total Price = ", it ?: 0f, "$")
            }
            rvShopping.apply {
                adapter = shoppingAdapter
                layoutManager = LinearLayoutManager(requireContext())
                ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
            }
            viewModel.shoppingItems.observe(viewLifecycleOwner) {
                shoppingAdapter.submitList(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShoppingItemClick(shoppingItem: ShoppingItem) {
        val action = ShoppingFragDirections.actionShoppingFragToAddShoppingItemFrag()
        findNavController().navigate(action)
    }
    
    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = shoppingAdapter.currentList[position]
            viewModel.deleteShoppingItem(item)
            Snackbar.make(requireView(),"Deleted",Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.insertShoppingItem(item)
                }
                show()
            }
        }

    }

}