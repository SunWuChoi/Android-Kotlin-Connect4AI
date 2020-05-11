package edu.towson.connect4_ai.interfaces

import android.graphics.Bitmap
import edu.towson.connect4_ai.models.Account
import kotlinx.coroutines.CoroutineScope

interface IMenuController : CoroutineScope {
    suspend fun fetchIcon(url: String): Bitmap
    suspend fun checkCache(icon: String): Bitmap?
    suspend fun cacheIcon(filename: String, icon: Bitmap)
    fun getImageFilename(url: String): String
}