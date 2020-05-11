package edu.towson.connect4_ai.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.findNavController
import edu.towson.connect4_ai.R
import edu.towson.connect4_ai.RankingActivity
import edu.towson.connect4_ai.interfaces.IAccountController
import edu.towson.connect4_ai.models.Account
import kotlinx.android.synthetic.main.activity_ranking.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*









/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private lateinit var accountController: IAccountController
    lateinit var currentUser: Account

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when(context){
            is IAccountController -> accountController = context
            else -> throw Exception("IAccountController expected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_btn.setOnClickListener{ loginBtn() }


    }

    private fun loginBtn() {
        val username = login_username_input.editableText.toString()
        val password = login_password_input.editableText.toString()

        accountController.launch {
            val accounts = accountController.accounts.getAll()
            if(checkLogin(username,password,accounts)){
                currentUser = getUser(username,accounts)
                accountController.login(currentUser)
                Toast.makeText(activity,"Logged in as ${currentUser.username}", Toast.LENGTH_LONG).show()
                hideKeyboard(activity as RankingActivity)
                findNavController(nav_host_fragment)
                    .navigate(R.id.action_loginFragment_to_accountListFragment)
            } else {
                Toast.makeText(activity,"Wrong Username or Password", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun checkLogin(username: String, password: String, list: List<Account> ): Boolean{
        for(account in list){
            if(account.username == username){
                if(account.password == password){
                    return true
                }
            }
        }
        return false
    }

    private fun getUser(username: String, list: List<Account>): Account{
        for(account in list){
            if(account.username == username){
                return account
            }
        }

        // this will never reach since this function is only
        // called when there is a match in the database
        return Account(UUID.randomUUID(), "null", "null",0,0 )
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }




}
