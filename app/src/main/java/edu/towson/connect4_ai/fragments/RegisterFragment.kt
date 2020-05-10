package edu.towson.connect4_ai.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import edu.towson.connect4_ai.R
import edu.towson.connect4_ai.interfaces.IAccountController
import edu.towson.connect4_ai.models.Account
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private lateinit var accountController: IAccountController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when(context){
            is IAccountController -> accountController = context
            else -> throw Exception("IAccountController expected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_btn.setOnClickListener { handleAddAccountClick() }


    }


    private fun handleAddAccountClick() {

        val newAccount = Account(
            id = UUID.randomUUID(),
            username = register_username_input.editableText.toString(),
            password = register_password_input.editableText.toString(),
            victory = 0,
            gamesPlayed = 0
        )

        accountController.launch {
            try {
                val accounts = accountController.accounts.getAll()
                if(!checkExisting(newAccount.username, accounts)) {
                    accountController.addNewAccount(newAccount)
                    Toast.makeText(activity,"Created new account: ${newAccount.username}", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(activity,"${newAccount.username} already exists!", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception){

            }
        }

    }

    private fun checkExisting(inputUsername: String, list: List<Account>): Boolean{
        for(account in list){
            if(account.username == inputUsername){
                return true
            }
        }
        return false
    }

}
