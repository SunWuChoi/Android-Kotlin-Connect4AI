package edu.towson.connect4_ai.interfaces

import edu.towson.connect4_ai.models.Account


interface IAccountRepository {
    fun getCount(): Int
    fun getAccount(idx: Int): Account
    fun getAll(): List<Account>
    fun addAccount(account: Account)
}