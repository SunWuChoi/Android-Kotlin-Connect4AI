package edu.towson.connect4_ai.database

import android.content.Context
import androidx.room.Room
import edu.towson.connect4_ai.interfaces.IAccountRepository
import edu.towson.connect4_ai.models.Account
import kotlinx.coroutines.delay
import java.lang.Exception

class AccountDatabaseRepository(ctx: Context) : IAccountRepository {

    private val accountList: MutableList<Account> = mutableListOf()
    private val db: AccountDatabase

    init {
        db = Room.databaseBuilder(
            ctx,
            AccountDatabase::class.java,
            "accounts.db"
        ).build()   //allowMainThreadQueries()
    }

    override fun getCount(): Int {
        return accountList.size
    }

    override fun getAccount(idx: Int): Account {
        return accountList.get(idx)
    }

    override suspend fun getAll(): List<Account> {
        if(accountList.size == 0){
            refreshAccountList()
        }
        return accountList
    }

    override suspend fun remove(account: Account) {
        //delay(2000)
        // this will throw an exception randomly
        //if (System.currentTimeMillis() % 2 == 0L) throw Exception()
        db.AccountDao().deleteAccount(account)
        refreshAccountList()
    }

    override suspend fun replace(account: Account) {
        //delay(2000)
        // this will throw an exception randomly
        //if (System.currentTimeMillis() % 2 == 0L) throw Exception()
        db.AccountDao().updateAccount(account)
        refreshAccountList()
    }

    override suspend fun addAccount(account: Account) {
        db.AccountDao().addAccount(account)
        refreshAccountList()
    }

    suspend fun refreshAccountList(){
        accountList.clear()
        val accounts = db.AccountDao().getAllAccounts()
        accountList.addAll(accounts)
    }


}