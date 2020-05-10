package edu.towson.connect4_ai.database

import androidx.room.*
import edu.towson.connect4_ai.models.Account
import java.util.*

@Dao
interface AccountDao{

    @Insert
    suspend fun addAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("select id, username, password, victory, gamesPlayed from Account")
    suspend fun getAllAccounts(): List<Account>
}

//Type Converter
class UUIDConverter{
    @TypeConverter
    fun fromString(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun toString(uuid: UUID): String{
        return uuid.toString()
    }
}


//Database Class
@Database(entities = arrayOf(Account::class), version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class AccountDatabase: RoomDatabase(){
    abstract fun AccountDao(): AccountDao
}