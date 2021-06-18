package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubRepositoriesRepo
import ru.geekbrains.lyagaev.popularlibraries.intarface.INetworkStatus
import ru.geekbrains.lyagaev.popularlibraries.model.GithubRepository
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource
import ru.geekbrains.lyagaev.popularlibraries.room.RoomGithubRepository
import ru.geekbrains.lyagaev.popularlibraries.room.db.Database
import java.lang.RuntimeException

//Практическое задание 1 - вытащить кэширование в отдельный класс RoomRepositoriesCache и внедрить его сюда через интерфейс IRepositoriesCache
class RetrofitGithubRepositoriesRepo(val api: IDataSource, val networkStatus: INetworkStatus, val db: Database) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            user.reposUrl?.let { url ->
                api.getRepositories(url)
                    .flatMap { repositories ->
                        Single.fromCallable {
                            val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
                            val roomRepos = repositories.map { RoomGithubRepository(
                                it.id ?: "",
                                it.name ?: "",
                                it.forksCount ?: "",
                                roomUser.id) }
                            db.repositoryDao.insert(roomRepos)
                            repositories
                        }
                    }
            } ?: Single.error<List<GithubRepository>>(RuntimeException("User has no repos url")).subscribeOn(Schedulers.io())
        } else {
            Single.fromCallable {
                val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
                db.repositoryDao.findForUser(roomUser.id).map { GithubRepository(it.id, it.name, it.forksCount) }
            }

        }
    }.subscribeOn(Schedulers.io())
}

