package edu.towson.connect4_ai

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import edu.towson.connect4_ai.models.Account
import kotlinx.android.synthetic.main.menu_activity.*
import kotlinx.coroutines.launch

class MainMenuActivity : AppCompatActivity() {
    lateinit var currentUser: Account
    private var loggedIn = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

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
            SILVA -> {
                // process the result of silva mode
                when(requestCode){
                    10 -> { // lose
                        currentUser.gamesPlayed++
                    }
                    20 -> { // win
                        currentUser.gamesPlayed++
                        currentUser.victory++
                    }

                }

            }
        }

    }

    fun logout() {

    }

    companion object {
        val LEADERBOARD_CODE = 1
        val SILVA = 2
    }
}