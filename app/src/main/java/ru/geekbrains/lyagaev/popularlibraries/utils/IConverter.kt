package ru.geekbrains.lyagaev.popularlibraries.utils

import io.reactivex.rxjava3.core.Completable

interface IConverter {
    fun convert(image: Image): Completable
}