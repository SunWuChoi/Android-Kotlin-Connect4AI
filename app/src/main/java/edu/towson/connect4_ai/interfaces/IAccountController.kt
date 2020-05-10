package edu.towson.connect4_ai.interfaces

import android.graphics.Bitmap
import edu.towson.connect4_ai.models.Account
import kotlinx.coroutines.CoroutineScope

interface IAccountController : CoroutineScope {
    suspend fun deleteAccount(idx: Int)
    suspend fun addNewAccount(account: Account)
    suspend fun handleEditedAccount(account: Account)
    suspend fun fetchAccounts(): List<Account>
    suspend fun fetchIcon(url: String): Bitmap
    suspend fun checkCache(icon: String): Bitmap?
    suspend fun cacheIcon(filename: String, icon: Bitmap)
    fun login(account: Account)
    fun editAccount(idx: Int)
    fun getAccountForEdit(): Account?
    fun clearEditingAccount()
    fun queryMediaStore()
    fun launchNewRegistrationScreen()
    fun launchLoginScreen()
    val accounts: IAccountRepository
}