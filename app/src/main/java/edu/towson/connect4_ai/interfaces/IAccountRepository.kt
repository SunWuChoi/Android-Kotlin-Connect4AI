package edu.towson.connect4_ai.interfaces

import edu.towson.connect4_ai.models.Account


interface IAccountRepository {
    fun getCount(): Int
    fun getAccount(idx: Int): Account
    suspend fun getAll(): List<Account>
    suspend fun remove(account: Account)
    suspend fun replace(account: Account)
    suspend fun addAccount(account: Account)
}