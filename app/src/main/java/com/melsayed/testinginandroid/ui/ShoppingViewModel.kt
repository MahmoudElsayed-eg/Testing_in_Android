package com.melsayed.testinginandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.melsayed.testinginandroid.data.local.ShoppingItem
import com.melsayed.testinginandroid.data.remote.PixabayPhoto
import com.melsayed.testinginandroid.other.Constants
import com.melsayed.testinginandroid.other.Event
import com.melsayed.testinginandroid.other.Resource
import com.melsayed.testinginandroid.repositories.DefaultShoppingRepo
import com.melsayed.testinginandroid.repositories.ShoppingRepo
import com.melsayed.testinginandroid.retrofit.PixabayResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repo: ShoppingRepo) : ViewModel() {
    val shoppingItems = repo.observeAllShoppingItem()
    val totalPrice = repo.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<PixabayResponse>>>()
    val images: LiveData<Event<Resource<PixabayResponse>>> = _images

    private val _curImgUrl = MutableLiveData<String>()
    val curImgUrl: LiveData<String> = _curImgUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurImgUrl(url: String) {
        _curImgUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repo.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repo.insertShoppingItem(shoppingItem)
    }

    fun validateShoppingItem(name: String, amount: String, price: String) {
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The field is empty", null)))
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("name is too long", null)))
            return
        }
        if (price.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("price is too long", null)))
            return
        }
        try {
            amount.toInt()
        } catch (e:Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("amount is too high", null)))
            return
        }
        val shoppingItem = ShoppingItem(name = name,amount = amount.toInt(),price = price.toFloat(),imgUrl = _curImgUrl.value?:"")
        setCurImgUrl("")
        insertShoppingItem(shoppingItem)
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))

    }

    fun searchImages(query: String) {
        if (query.isEmpty()) {
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repo.searchPhoto(query,1,2)
            _images.value = Event(response)
        }
    }

    fun searchImagesFromApi(query: String): LiveData<PagingData<PixabayPhoto>> {
        return (repo as DefaultShoppingRepo).searchPhoto(query)
    }
}