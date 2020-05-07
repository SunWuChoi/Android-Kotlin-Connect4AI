package edu.towson.connect4_ai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.towson.connect4_ai.R
import edu.towson.connect4_ai.database.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_login.login_password_et
import kotlinx.android.synthetic.main.fragment_user_login.login_screen_login_btn
import kotlinx.android.synthetic.main.fragment_user_login.login_username_et
import kotlinx.android.synthetic.main.user_login.*

class LoginActivityFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    private lateinit var helper: DatabaseHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_screen_login_btn.setOnClickListener {
            if(helper.userPresent(login_username_et.text.toString(), login_password_et.text.toString())){
                Toast.makeText(this, "Login Success: Welcome: ${login_username_et}", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show()
            }
        }

        sign_up_btn.setOnClickListener { launchRegistrationActivity() }
    }

    fun launchRegistrationActivity(){
        val intent = Intent(this, RegistrationActivityFragment::class.java)
        startActivity(intent)
    }
}