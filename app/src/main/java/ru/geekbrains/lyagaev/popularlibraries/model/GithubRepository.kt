package ru.geekbrains.lyagaev.popularlibraries.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepository(
    @Expose val id: String,
    @Expose val name: String? = null,
    @Expose val forksCount: String? = null,
    @Expose val fullName: String? = null
) : Parcelable
