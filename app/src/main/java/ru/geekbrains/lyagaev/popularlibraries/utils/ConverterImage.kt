package ru.geekbrains.lyagaev.popularlibraries.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class ConverterImage(private val context: Context?) : IConverter  {
    override fun convert(image: Image): Completable = Completable.fromAction {

        context?.let { context ->
            val bitmap = BitmapFactory.decodeByteArray(image.data, 0, image.data.size)
            val dstFile = File(context.getExternalFilesDir(null), "imageTest.png")
            FileOutputStream(dstFile).use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }

    }.subscribeOn(Schedulers.io())


}