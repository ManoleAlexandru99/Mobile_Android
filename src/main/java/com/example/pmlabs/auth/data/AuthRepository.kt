package com.example.pmlabs.auth.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.pmlabs.auth.data.remote.RemoteAuthDataSource
import com.example.pmlabs.core.Api
import com.example.pmlabs.core.Result
import com.example.pmlabs.core.TAG
import com.example.pmlabs.core.TokenStorage

object AuthRepository {

    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = TokenStorage.instance()?.fetchValueString("token") != null

    init {
        Api.tokenInterceptor.token = TokenStorage.instance()?.fetchValueString("token")
        user = null
    }

    fun logout() {

        user = null
        TokenStorage.instance()?.deleteValueString("token")
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<TokenHolder> {
        val user = User(username, password)
        val result = RemoteAuthDataSource.login(user)
        if (result is Result.Success<TokenHolder>) {
            TokenStorage.instance()?.storeValueString("token", result.data.token)
            Log.v(TAG, "Token Stored")
            setLoggedInUser(user, result.data)
        }
        return result
    }

    private fun setLoggedInUser(user: User, tokenHolder: TokenHolder) {
        this.user = user
        Api.tokenInterceptor.token = tokenHolder.token

    }
}
