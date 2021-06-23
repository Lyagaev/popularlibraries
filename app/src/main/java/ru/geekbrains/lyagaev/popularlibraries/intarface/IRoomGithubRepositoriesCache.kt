package ru.geekbrains.lyagaev.popularlibraries.intarface

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.lyagaev.popularlibraries.model.GithubRepository
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

interface IRoomGithubRepositoriesCache {
    fun getUserRepos(user: GithubUser) : Single<List<GithubRepository>>
    fun putUserRepos(user: GithubUser, repository: List<GithubRepository>) : Completable
}