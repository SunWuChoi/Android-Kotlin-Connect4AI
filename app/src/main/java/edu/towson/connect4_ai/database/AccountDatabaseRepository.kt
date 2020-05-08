package edu.towson.connect4_ai.database

import android.content.Context
import androidx.room.Room
import edu.towson.connect4_ai.interfaces.IAccountRepository
import edu.towson.connect4_ai.models.Account

class AccountDatabaseRepository(ctx: Context) : IAccountRepository {

    private val accountList: MutableList<Account> = mutableListOf()
    private val db: AccountDatabase

    init {
        db = Room.databaseBuilder(
            ctx,
            AccountDatabase::class.java,
            "accounts.db"
        ).allowMainThreadQueries().build()
        refreshAccountList()
    }

    override fun getCount(): Int {
        return accountList.size
    }

    override fun getAccount(idx: Int): Account {
        return accountList.get(idx)
    }

    override fun getAll(): List<Account> {
        return accountList
    }

    override fun addAccount(account: Account) {
        db.AccountDao().addAccount(account)
        refreshAccountList()
    }

    private fun refreshAccountList(){
        accountList.clear()
        val accounts = db.AccountDao().getAllAccounts()
        accountList.addAll(accounts)
    }


}