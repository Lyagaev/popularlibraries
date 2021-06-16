package ru.geekbrains.lyagaev.popularlibraries.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.view2.UserView

class UserPresenter(
        private val router: Router,
        private val user: GithubUser
) : MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user.login?.let { viewState.showLogin(it) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}