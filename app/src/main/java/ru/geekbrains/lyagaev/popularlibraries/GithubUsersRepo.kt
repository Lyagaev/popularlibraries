package ru.geekbrains.lyagaev.popularlibraries

import io.reactivex.rxjava3.core.Observable
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

class GithubUsersRepo {
    private val repositories =  Observable.just(
            GithubUser("login1"),
            GithubUser("login2"),
            GithubUser("login3"),
            GithubUser("login4"),
            GithubUser("login5")
    )

    fun getUsers(): Observable<GithubUser> = repositories

}