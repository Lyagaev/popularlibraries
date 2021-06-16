package ru.geekbrains.lyagaev.popularlibraries.intarface

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}