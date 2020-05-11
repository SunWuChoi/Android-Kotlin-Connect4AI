package edu.towson.connect4_ai.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.towson.connect4_ai.AccountsAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import edu.towson.connect4_ai.R
import edu.towson.connect4_ai.interfaces.IAccountController
import kotlinx.android.synthetic.main.fragment_account_list.*
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class AccountListFragment : Fragment() {

    private lateinit var  accountController: IAccountController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when(context){
            is IAccountController -> accountController = context
            else -> throw Exception("IAccountController expected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AccountsAdapter(accountController)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(context)

        to_register_menu_btn?.setOnClickListener { accountController.launchNewRegistrationScreen() }
        to_login_menu_btn?.setOnClickListener { accountController.launchLoginScreen() }

    }

    override fun onResume() {
        super.onResume()

        accountController.launch {
            accountController.accounts.getAll()
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }
}
