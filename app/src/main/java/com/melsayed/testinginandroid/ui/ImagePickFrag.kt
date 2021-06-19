package com.melsayed.testinginandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.melsayed.testinginandroid.Util.autoFitColumns
import com.melsayed.testinginandroid.adapters.ImagesPagerAdapter
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.databinding.FragmentImagePickBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFrag : Fragment(),ImagesPagerAdapter.OnImageClicked {
    private var _binding: FragmentImagePickBinding? = null
    private val binding get() = _binding!!
    lateinit var imageAdapter: ImagesPagerAdapter
//    private val viewModel by activityViewModels<ShoppingViewModel>()
    lateinit var viewModel:ShoppingViewModel
    @Inject
    lateinit var glide:RequestManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImagePickBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        imageAdapter = ImagesPagerAdapter(glide,this)
        setupRecycleView()
        binding.etSearchImage.addTextChangedListener { editable ->
            viewModel.searchImagesFromApi(editable.toString()).observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    imageAdapter.submitData(it)
                }
            }
        }
        imageAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.Error) {
                Toast.makeText(requireContext(),"Error Loading Images, Check Internet",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setupRecycleView() {
        binding.rvSearch.apply {
            adapter = imageAdapter
            autoFitColumns(100)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onImageClick(pixabayPhoto: PixabayPhoto) {
        viewModel.setCurImgUrl(pixabayPhoto.previewURL)
        findNavController().popBackStack()
    }

}