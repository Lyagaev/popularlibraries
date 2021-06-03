package ru.geekbrains.lyagaev.popularlibraries.presenter

import ru.geekbrains.lyagaev.popularlibraries.view2.IItemView
import ru.geekbrains.lyagaev.popularlibraries.view2.UserItemView

interface IListPresenter<V : IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IUserListPresenter : IListPresenter<UserItemView>