package ru.geekbrains.lyagaev.popularlibraries.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IGithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.intarface.INetworkStatus
import ru.geekbrains.lyagaev.popularlibraries.intarface.IRoomGithubUsersCache
import ru.geekbrains.lyagaev.popularlibraries.network.IDataSource

//Практическое задание 1 - вытащить кэширование в отдельный класс RoomUserCache и внедрить его сюда через интерфейс IUserCache
class RetrofitGithubUsersRepo(val api: IDataSource, val networkStatus: INetworkStatus, val cache: IRoomGithubUsersCache) : IGithubUsersRepo {
    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    cache.putUsers(users).toSingleDefault(users)
                }
        } else {
            cache.getUsers()
        }
    }.subscribeOn(Schedulers.io())
}