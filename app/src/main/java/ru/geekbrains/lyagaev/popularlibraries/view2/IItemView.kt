package ru.geekbrains.lyagaev.popularlibraries.view2

interface IItemView {
    var pos: Int
}

interface UserItemView: IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}