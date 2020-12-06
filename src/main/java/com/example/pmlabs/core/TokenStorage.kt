package com.example.pmlabs.core

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences


class TokenStorage   {
  var context: Context? = null
  var sharedPref: SharedPreferences? = null
  var sharedPrefEditor: SharedPreferences.Editor? = null

  fun configSessionUtils(context: Context) {
    this.context = context
    sharedPref = context.getSharedPreferences("loginDetails", Activity.MODE_PRIVATE)
    sharedPrefEditor = sharedPref?.edit()
  }

  fun storeValueString(key: String?, value: String?) {
    sharedPrefEditor?.putString(key, value)
    sharedPrefEditor!!.apply()
  }

  fun fetchValueString(key: String?): String? {
    return sharedPref!!.getString(key, null)
  }

  fun deleteValueString(key: String?) {
    sharedPref!!.edit().remove(key).apply()
  }

  companion object {
    var _instance: TokenStorage? = null
    fun instance(context: Context): TokenStorage? {
      if (_instance == null) {
        _instance = TokenStorage()
        _instance!!.configSessionUtils(context)
      }
      return _instance
    }

    fun instance(): TokenStorage? {
      return _instance
    }
  }

}