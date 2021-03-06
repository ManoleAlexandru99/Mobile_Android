package com.example.manolealexandru_lab1.todo.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.manolealexandru_lab1.todo.data.ItemRepository
import com.example.manolealexandru_lab1.core.TAG
import com.example.manolealexandru_lab1.todo.data.Item
import java.util.*

class ItemEditViewModel : ViewModel() {
    private val date = Date();
    private val mutableItem = MutableLiveData<Item>().apply { value = Item("", "" )}  //,date , true ) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val item: LiveData<Item> = mutableItem
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadItem...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableItem.value = ItemRepository.load(itemId)
                Log.i(TAG, "loadItem succeeded")
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItem failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(text: String) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem...");
            val item = mutableItem.value ?: return@launch
            item.text = text
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (item.id.isNotEmpty()) {
                    mutableItem.value = ItemRepository.update(item)
                } else {
                    mutableItem.value = ItemRepository.save(item)
                }
                Log.i(TAG, "saveOrUpdateItem succeeded");
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateItem failed", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }
}
