package ru.geekbrains.lyagaev.popularlibraries.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import ru.geekbrains.lyagaev.popularlibraries.GithubUsersRepo
import ru.geekbrains.lyagaev.popularlibraries.model.GithubUser
import ru.geekbrains.lyagaev.popularlibraries.navigation.IScreens
import ru.geekbrains.lyagaev.popularlibraries.utils.IConverter
import ru.geekbrains.lyagaev.popularlibraries.utils.Image
import ru.geekbrains.lyagaev.popularlibraries.view2.UserItemView
import ru.geekbrains.lyagaev.popularlibraries.view2.UsersView

var disposable: Disposable? = null

class UsersPresenter(private val usersRepo: GithubUsersRepo, private val router: Router, private val screens: IScreens, private val converter: IConverter, private val mainThreadScheduler: Scheduler) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
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
        disposable =
                usersRepo
                        .getUsers()
                        ?.subscribe( { users -> subscribeUsers(users) }, { it.printStackTrace() })
    }

    private fun subscribeUsers(users: List<GithubUser>) {
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    fun convertClick() {
        viewState.pickImage()
    }

    var conversionDisposable: Disposable? = null
    fun imageSelected(image: Image) {
        conversionDisposable = converter.convert(image)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.convertSuccess()
            },{

            })
    }

}
