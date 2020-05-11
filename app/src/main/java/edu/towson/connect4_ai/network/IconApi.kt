package edu.towson.connect4_ai.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import edu.towson.connect4_ai.interfaces.IMenuController
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

interface IIconApi {
    suspend fun fetchIcon(iconUrl: String): Deferred<Bitmap>
    fun getImageFilename(url: String): String
}

class IconApi(val controller: IMenuController): IIconApi {

    private val client: OkHttpClient = OkHttpClient()

    override suspend fun fetchIcon(iconUrl: String): Deferred<Bitmap> {
        return controller.async(Dispatchers.IO) {
            val filename = getImageFilename(iconUrl)
            val bitmap = controller.checkCache(filename)
            if (bitmap != null) {
                bitmap

            } else {
                val request = Request.Builder()
                    .url(iconUrl)
                    .get()
                    .build()
                val stream = client.newCall(request).execute().body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(stream)
                if (bitmap != null) {
                    controller.cacheIcon(filename, bitmap)
                }
                bitmap
            }

            }
        }

    override fun getImageFilename(url: String): String {
        val urlObj = URL(url)
        val query : String? = urlObj.toString().drop(25)
        return query.toString()
    }

}