package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource

class RetrofitGithubUsersRepo(val api: IDataSource): IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}
