package ru.geekbrains.lyagaev.popularlibraries.view2

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UsersView : MvpView {
    fun init()
    fun updateList()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun pickImage()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun convertSuccess()
}