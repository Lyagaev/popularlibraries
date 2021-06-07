package ru.geekbrains.lyagaev.popularlibraries.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.geekbrains.lyagaev.popularlibraries.GithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.navigation.IScreens
import ru.geekbrains.lyagaev.popularlibraries.view2.UserItemView
import ru.geekbrains.lyagaev.popularlibraries.view2.UsersView

var disposable: Disposable? = null

class UsersPresenter(private val usersRepo: GithubUsersRepo, private val router: Router, private val screens: IScreens) : MvpPresenter<UsersView>() {
    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            disposable = Single.just(users[view.pos])
                .filter() { it.login != "login1" }
                .subscribe({
                    onBindViewSuccess(view, it.login)
                }, ::onBindViewError)
        }

        private fun onBindViewSuccess(view: UserItemView, login: String) {
            view.setLogin(login)
        }

        private fun onBindViewError(error: Throwable) {
            println("onError: ${error.message}")
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(screens.user(user))
        }
    }

    private fun loadData() {
        usersRepo.getUsers()
            .doOnComplete(::onLoadDataComplete
            ).subscribe(
                ::onLoadDataSuccess
            )
    }

    private fun onLoadDataComplete() {
        viewState.updateList()
    }

    private fun onLoadDataSuccess(user: GithubUser) {
        usersListPresenter.users.add(user)
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

}
