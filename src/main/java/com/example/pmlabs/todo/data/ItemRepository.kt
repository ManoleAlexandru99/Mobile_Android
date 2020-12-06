package com.example.pmlabs.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pmlabs.todo.data.local.ItemDao
import com.example.pmlabs.core.Result
import com.example.pmlabs.core.TAG
import com.example.pmlabs.todo.data.remote.ItemApi

class ItemRepository(private val itemDao: ItemDao) {

    val items = itemDao.getAll()

    suspend fun refresh(): Result<Boolean> {
        try {

            val items2 = ItemApi.service.find()
            if(items2.size == 0) {
                itemDao.deleteAll()
            }
            else {
                for (item in items2) {
                    itemDao.insert(item)
                }
            }

            return Result.Success(true)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    fun getById(itemId: String): LiveData<Item> {
        return itemDao.getById(itemId)
    }

    suspend fun save(item: Item): Result<Item> {
        try {
            val createdItem = ItemApi.service.create(item)
            Log.v(TAG, createdItem.toString())
            itemDao.insert(createdItem)
            return Result.Success(createdItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun update(item: Item): Result<Item> {
        try {
            val updatedItem = ItemApi.service.update(item._id, item)
            Log.v(TAG, updatedItem.toString())
            if(updatedItem.text == "") {
                itemDao.update(item)
            }
            else {
                itemDao.update(updatedItem)
            }
            return Result.Success(updatedItem)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }
}