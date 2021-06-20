package ru.geekbrains.lyagaev.popularlibraries.intarface

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

interface IRoomGithubUsersCache {
    fun getUsers(): Single<List<GithubUser>>
    fun putUsers(users: List<GithubUser>) : Completable
}