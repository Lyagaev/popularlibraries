package ru.geekbrains.lyagaev.popularlibraries.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url
import ru.geekbrains.lyagaev.popularlibraries.repository.GithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>

    @GET
    fun getRepositories(@Url url: String): Single<List<GithubUsersRepo>>
}