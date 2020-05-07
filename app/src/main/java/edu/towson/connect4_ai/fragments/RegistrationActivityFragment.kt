package edu.towson.connect4_ai.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.towson.connect4_ai.R
import edu.towson.connect4_ai.database.DatabaseHelper
import kotlinx.android.synthetic.main.fragment_user_registration.*

class RegistrationActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_registration, container, false)
    }

    private lateinit var helper: DatabaseHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        new_register_btn.setOnClickListener {
            helper.insertUserData(reg_username_et.text.toString(), reg_email_et.text.toString(), reg_password_et.text.toString())
        }
    }


}