package ru.geekbrains.lyagaev.popularlibraries.navigation

import com.github.terrakok.cicerone.Screen
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
}