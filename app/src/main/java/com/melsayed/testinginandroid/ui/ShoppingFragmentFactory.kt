package com.melsayed.testinginandroid.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.melsayed.testinginandroid.adapters.ImagesPagerAdapter
import javax.inject.Inject

//class ShoppingFragmentFactory @Inject constructor(
//    private val adapter: ImagesPagerAdapter
//) : FragmentFactory() {
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        return when (className) {
//            ImagePickFrag::class.java.name ->
//                ImagePickFrag(adapter)
//            else ->
//                super.instantiate(classLoader, className)
//        }
//    }
//}