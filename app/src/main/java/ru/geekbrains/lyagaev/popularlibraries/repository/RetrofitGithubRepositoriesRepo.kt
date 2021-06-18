package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubRepositoriesRepo
import ru.geekbrains.lyagaev.popularlibraries.model.GithubRepository
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource
import java.lang.RuntimeException

class RetrofitGithubRepositoriesRepo(private val api: IDataSource): IGithubRepositoriesRepo {

     override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> = user.reposUrl?.let {
         api.getRepositories(it).subscribeOn(Schedulers.io())
     } ?: Single.error<List<GithubRepository>>(RuntimeException("Нет ссылки на репозиторий")).subscribeOn(Schedulers.io())

}

