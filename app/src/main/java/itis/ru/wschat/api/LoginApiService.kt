package itis.ru.wschat.api

import io.reactivex.Single
import itis.ru.wschat.Const
import itis.ru.wschat.models.LoginResponse
import itis.ru.wschat.models.User
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService{
    @POST("login")
    fun login(@Body user: User): Single<LoginResponse>
}
