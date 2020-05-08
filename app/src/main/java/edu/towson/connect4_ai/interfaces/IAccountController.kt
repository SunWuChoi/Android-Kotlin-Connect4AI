package edu.towson.connect4_ai.interfaces

import edu.towson.connect4_ai.models.Account

interface IAccountController {
    fun lauchNewRegistrationScreen()
    val accounts: IAccountRepository
    fun addNewAccount(account: Account)
    fun editAccount(idx:Int)
    fun getAccountForEdit(): Account?
    fun handleEditedAccount(account: Account)
    fun clearEditingAccount()
}