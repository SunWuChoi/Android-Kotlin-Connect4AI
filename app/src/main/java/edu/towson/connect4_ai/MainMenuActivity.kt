package edu.towson.connect4_ai

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.google.gson.Gson
import edu.towson.connect4_ai.interfaces.IMenuController
import edu.towson.connect4_ai.models.Account
import edu.towson.connect4_ai.network.IIconApi
import edu.towson.connect4_ai.network.IconApi
import kotlinx.android.synthetic.main.menu_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainMenuActivity : AppCompatActivity(), IMenuController {
    override val coroutineContext: CoroutineContext
        get() = lifecycleScope.coroutineContext

    lateinit var currentUser: Account
    private lateinit var iconApi: IIconApi
    private var loggedIn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        iconApi = IconApi(this)


        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO){
                    val bitmap = fetchIcon("https://i.ibb.co/3zGf45M/Connect4-Icon.jpg")
                    if(bitmap != null) {
                        app_icon.setImageBitmap(bitmap)
                        progressBar.visibility = View.INVISIBLE
                        app_icon.visibility = View.VISIBLE
                    }
                }

            } catch (e: Exception){
                //Toast.makeText(this@MainMenuActivity,"Icon error", Toast.LENGTH_SHORT).show()
            }
        }

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(JOB_ID, ComponentName(this, CService::class.java))
        jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        jobInfo.setMinimumLatency(10 * 1000)
        scheduler.schedule(jobInfo.build())

        MessageQueue.Channel.observe(this, { success ->
            Log.d(TAG, "Received a message : $success")
            NotificationManagerCompat.from(this).cancel(CService.NOTIF_ID)
        })


        twoPlayerMode_btn.setOnClickListener{ launchTwoPlayerMode() }
        onePlayerMode_btn.setOnClickListener{ launchVsSilvaMode() }
        Leaderboard_btn.setOnClickListener { launchLeaderboardMode() }
    }

    fun launchTwoPlayerMode(){
        val intent = Intent(this, TwoPlayerModeActivity::class.java)
        startActivity(intent)
    }

    fun launchVsSilvaMode(){
        if(loggedIn){
            val intent = Intent(this, VsSilvaActivity::class.java)
            startActivityForResult(intent, SILVA)
        } else {
            Toast.makeText(this, "Login first at Leaderboard menu", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchLeaderboardMode(){
        val intent = Intent(this, RankingActivity::class.java)
        if(loggedIn) {
            intent.putExtra(RankingActivity.CURRENT_USER_KEY, Gson().toJson(currentUser))
        }
        startActivityForResult(intent, LEADERBOARD_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Toast.makeText(this, "request code: $requestCode result  code: $resultCode", Toast.LENGTH_SHORT).show()
        when(requestCode) {
            LEADERBOARD_CODE -> {
                when(resultCode){
                    1 -> {  // logged in
                        val user = Gson().fromJson<Account>(data?.getStringExtra(RankingActivity.CURRENT_USER_KEY), Account::class.java)
                        currentUser = user
                        loggedIn = true
                    }
                    0 -> {  // nothing logged in
                    }
                }
            }
            SILVA -> {  // process the result of silva mode
                when(resultCode){
                    10 -> { // lose
                        currentUser.gamesPlayed++
                        //Toast.makeText(this, "Lost", Toast.LENGTH_SHORT).show()

                        launchLeaderboardMode()

                    }
                    20 -> { // win
                        currentUser.gamesPlayed++
                        currentUser.victory++
                        //Toast.makeText(this, "won", Toast.LENGTH_SHORT).show()
                        launchLeaderboardMode()
                    }
                    30 -> { // tie
                        currentUser.gamesPlayed++
                        currentUser.victory++

                        currentUser.gamesPlayed++
                        //Toast.makeText(this, "Tie", Toast.LENGTH_SHORT).show()
                        launchLeaderboardMode()
                    }
                    40 -> { // game ended without tie or win or lose
                        //nothing happens
                        //Toast.makeText(this, "exited", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }


    override suspend fun fetchIcon(url: String): Bitmap {
        return iconApi.fetchIcon(url).await()
    }

    override suspend fun checkCache(icon: String): Bitmap? {
        val file = File(cacheDir, icon)
        if(file.exists()) {
            val input = file.inputStream()
            return BitmapFactory.decodeStream(input)
        } else {
            return null
        }
    }

    override suspend fun cacheIcon(filename: String, icon: Bitmap) {
        val file = File(cacheDir, filename)
        val output = file.outputStream()
        icon.compress(Bitmap.CompressFormat.JPEG, 100, output)
    }

    override fun getImageFilename(url: String): String {
        return iconApi.getImageFilename(url)
    }

    companion object {
        val LEADERBOARD_CODE = 1
        val SILVA = 2
        val JOB_ID = 1
        val TAG = MainMenuActivity::class.java.simpleName
    }
}