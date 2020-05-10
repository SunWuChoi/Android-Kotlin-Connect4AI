package edu.towson.connect4_ai

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import edu.towson.connect4_ai.database.AccountDatabaseRepository
import edu.towson.connect4_ai.interfaces.IAccountController
import edu.towson.connect4_ai.interfaces.IAccountRepository
import edu.towson.connect4_ai.models.Account
import kotlinx.android.synthetic.main.fragment_account_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.CoroutineContext

class RankingActivity : AppCompatActivity(), IAccountController {
    override val coroutineContext: CoroutineContext
        get() = lifecycleScope.coroutineContext

    override lateinit var accounts: IAccountRepository
    lateinit var currentUser: Account

    private var loggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)


        accounts = AccountDatabaseRepository(this)

        val json : String? =  intent?.getStringExtra(CURRENT_USER_KEY)

        if(json != null){
            currentUser = Gson().fromJson<Account>(json, Account::class.java)
            launch {
                accounts.replace(currentUser)
            }
        }
    }




    override fun onBackPressed() {
        val intent = Intent()

        if(loggedIn){
            intent.putExtra(CURRENT_USER_KEY, Gson().toJson(currentUser) )
            setResult(1, intent ) // 1 means login success
            finish()
        } else {
            setResult(0, intent) // 0 means nothing logged in
            finish()
        }
        super.onBackPressed()
    }


    override fun login(account: Account) {
        loggedIn = true
        currentUser = account
    }

    override suspend fun addNewAccount(account: Account) {
        try {
            withContext(Dispatchers.IO) {
                accounts.addAccount(account)
            }
            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                findNavController(R.id.nav_host_fragment)
                    .popBackStack()
            } else {
                // update the recyclerview
                recyclerView.adapter?.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error: ${e.message}")
            Toast.makeText(this, "Failed to add new account", Toast.LENGTH_SHORT).show()
            throw e
        }
    }

    override suspend fun handleEditedAccount(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAccount(idx: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchAccounts(): List<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchIcon(url: String): Bitmap {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun checkCache(icon: String): Bitmap? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun cacheIcon(filename: String, icon: Bitmap) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun queryMediaStore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchNewRegistrationScreen() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_accountListFragment_to_registerFragment)
        }
    }

    override fun launchLoginScreen() {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_accountListFragment_to_loginFragment)
        }
    }

    override fun editAccount(idx: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccountForEdit(): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearEditingAccount() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    companion object {
        val TAG = RankingActivity::class.java.simpleName
        val CURRENT_USER_KEY = "CURRENT_USER_KEY"
    }
}