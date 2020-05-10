package edu.towson.connect4_ai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.towson.connect4_ai.interfaces.IAccountController
import edu.towson.connect4_ai.models.Account
import kotlinx.android.synthetic.main.leaderboard_view.view.*

class AccountsAdapter(private val controller: IAccountController) : RecyclerView.Adapter<AccountViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_view, parent, false)
        val viewHolder = AccountViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return controller.accounts.getCount()
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = controller.accounts.getAccount(position)
        holder.bindAccount(controller, account)
    }
}

class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindAccount(controller: IAccountController, account: Account) {
        itemView.username_leaderboard.text = account.username
        itemView.rank_leaderborad.text = "WIP"
        itemView.record_leaderboard.text = calculateWinrate(account).toString().plus("%")
    }

    fun calculateWinrate(account: Account) : Double {
        if(account.gamesPlayed != 0 ) {
            return (account.victory / account.gamesPlayed ).toDouble()
        } else {
            return 0.0
        }
    }
}