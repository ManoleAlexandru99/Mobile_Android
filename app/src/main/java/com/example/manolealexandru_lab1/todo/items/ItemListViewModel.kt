package com.example.manolealexandru_lab1.todo.items

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.manolealexandru_lab1.todo.data.ItemRepository
import com.example.manolealexandru_lab1.core.TAG
import com.example.manolealexandru_lab1.todo.data.Item

class ItemListViewModel : ViewModel() {
    private val mutableItems = MutableLiveData<List<Item>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Item>> = mutableItems
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createItem(position: Int): Unit {
        val list = mutableListOf<Item>()
        list.addAll(mutableItems.value!!)
        list.add(Item(position.toString(), "Item " + position))
        mutableItems.value = list
    }

    fun loadItems() {
        viewModelScope.launch {
            Log.v(TAG, "loadItems...");
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableItems.value = ItemRepository.loadAll()
                Log.d(TAG, "loadItems succeeded");
                mutableLoading.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItems failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}
