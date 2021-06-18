package ru.geekbrains.lyagaev.popularlibraries.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

@kotlinx.android.parcel.Parcelize
data class GithubUser(
    @Expose val id: String? = null,
    @Expose val login: String? = null,
    @Expose val avatarUrl: String? = null,
    @Expose val reposUrl: String? = null
) : Parcelable