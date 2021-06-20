package ru.geekbrains.lyagaev.popularlibraries.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.lyagaev.popularlibraries.intarface.IRoomGithubUsersCache
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.room.db.Database


class RoomGithubUserCache(val db: Database) : IRoomGithubUsersCache {

    override fun getUsers(): Single<List<GithubUser>> = Single.fromCallable {
        db.userDao.getAll().map { roomUser ->
            GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
        }
    }

    override fun putUsers(users: List<GithubUser>): Completable = Completable.fromAction {
        val roomUsers = users.map { user -> RoomGithubUser(user.id, user.login, user.avatarUrl ?: "", user.reposUrl ?: "") }
        db.userDao.insert(roomUsers)
    }.subscribeOn(Schedulers.io())
}