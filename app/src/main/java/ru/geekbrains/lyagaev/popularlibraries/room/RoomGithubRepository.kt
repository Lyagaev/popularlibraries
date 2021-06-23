package ru.geekbrains.lyagaev.popularlibraries.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGithubUser::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGithubRepository(
    @PrimaryKey var id: String,
    var name: String,
    var forksCount: String,
    var userId: String
)