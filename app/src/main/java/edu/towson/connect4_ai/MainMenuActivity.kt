package edu.towson.connect4_ai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.menu_activity.*

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        twoPlayerMode_btn.setOnClickListener{ launchTwoPlayerMode() }

    }

    fun launchTwoPlayerMode(){
        val intent = Intent(this, TwoPlayerModeActivity::class.java)
        startActivity(intent)
    }

}