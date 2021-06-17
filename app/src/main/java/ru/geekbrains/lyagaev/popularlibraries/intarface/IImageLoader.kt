package ru.geekbrains.lyagaev.popularlibraries.intarface

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}