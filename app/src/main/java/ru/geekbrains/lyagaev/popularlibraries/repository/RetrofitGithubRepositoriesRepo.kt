package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubRepositoriesRepo
import ru.geekbrains.lyagaev.popularlibraries.intarface.INetworkStatus
import ru.geekbrains.lyagaev.popularlibraries.intarface.IRoomGithubRepositoriesCache
import ru.geekbrains.lyagaev.popularlibraries.model.GithubRepository
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource
import ru.geekbrains.lyagaev.popularlibraries.room.RoomGithubRepository
import ru.geekbrains.lyagaev.popularlibraries.room.db.Database
import java.lang.RuntimeException

//Практическое задание 1 - вытащить кэширование в отдельный класс RoomRepositoriesCache и внедрить его сюда через интерфейс IRepositoriesCache
class RetrofitGithubRepositoriesRepo(val api: IDataSource, val networkStatus: INetworkStatus, private val cache: IRoomGithubRepositoriesCache) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            user.reposUrl?.let { url ->
                api.getRepositories(url)
                    .flatMap { repositories ->
                        cache.putUserRepos(user, repositories).toSingleDefault(repositories)
                    }
            } ?: Single.error<List<GithubRepository>>(RuntimeException("User has no repos url")).subscribeOn(Schedulers.io())
        } else {
            cache.getUserRepos(user)
        }
    }.subscribeOn(Schedulers.io())
}

