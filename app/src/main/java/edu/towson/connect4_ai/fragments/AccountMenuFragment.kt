package edu.towson.connect4_ai.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.towson.connect4_ai.R
import kotlinx.android.synthetic.main.account_view.*


//Not using this anymore. Instead will go straight to the login screen fragment



class AccountMenuFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_btn.setOnClickListener { launchLoginActivity() }
        reg_btn.setOnClickListener { launchRegisterActivity() }
    }

    fun launchRegisterActivity(){
        val intent = Intent(this, RegistrationActivityFragment::class.java)
        startActivity(intent)
    }

    fun launchLoginActivity(){
        val intent = Intent(this, LoginActivityFragment::class.java)
        startActivity(intent)
    }
}