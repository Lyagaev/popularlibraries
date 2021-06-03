package ru.geekbrains.lyagaev.popularlibraries

import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

class GithubUsersRepo {
    private val repositories = listOf(
            GithubUser("login1"),
            GithubUser("login2"),
            GithubUser("login3"),
            GithubUser("login4"),
            GithubUser("login5")
    )

    fun getUsers() : List<GithubUser> {
        return repositories
    }
}