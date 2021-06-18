package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.intarface.INetworkStatus
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource
import ru.geekbrains.lyagaev.popularlibraries.room.RoomGithubUser
import ru.geekbrains.lyagaev.popularlibraries.room.db.Database

//Практическое задание 1 - вытащить кэширование в отдельный класс RoomUserCache и внедрить его сюда через интерфейс IUserCache
class RetrofitGithubUsersRepo(val api: IDataSource, val networkStatus: INetworkStatus, val db: Database) : IGithubUsersRepo {
    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user -> RoomGithubUser(user.id ?: "", user.login ?: "", user.avatarUrl ?: "", user.reposUrl ?: "") }
                        db.userDao.insert(roomUsers)
                        users
                    }
                }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map { roomUser ->
                    GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
                }
            }
        }
    }.subscribeOn(Schedulers.io())
}