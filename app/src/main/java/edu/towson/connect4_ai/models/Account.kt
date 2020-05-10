package edu.towson.connect4_ai.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Account(
    @PrimaryKey
    val id: UUID,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "victory")
    var victory: Int,
    @ColumnInfo(name = "gamesPlayed")
    var gamesPlayed: Int
)
