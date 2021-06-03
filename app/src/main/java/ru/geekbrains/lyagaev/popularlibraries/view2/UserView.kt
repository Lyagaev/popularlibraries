package ru.geekbrains.lyagaev.popularlibraries.view2

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface UserView: MvpView {
    fun showLogin(userLogin:String)
}