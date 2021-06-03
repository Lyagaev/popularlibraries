package ru.geekbrains.lyagaev.popularlibraries.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.geekbrains.lyagaev.popularlibraries.UserFragment
import ru.geekbrains.lyagaev.popularlibraries.UsersFragment
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}