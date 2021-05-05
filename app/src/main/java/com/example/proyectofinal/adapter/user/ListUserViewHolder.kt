

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.base.BaseViewHolder
import com.example.proyectofinal.data.User
import com.example.proyectofinal.data.cart.LocalCart
import com.example.proyectofinal.fragments.ListUserFragmentDirections

class ListUserViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val txtOption = itemView.findViewById<TextView>(R.id.txtOptionUser)
    private val userView = itemView.findViewById<ConstraintLayout>(R.id.userView)
    private var stringOption = ""
    private var action = ListUserFragmentDirections


    fun bindUser(option: String, fragment: Fragment) {
        stringOption = option
        txtOption.text = option
        direction(fragment)
    }

    private fun direction(fragment: Fragment){
        userView.setOnClickListener {
            when {
                txtOption.text.toString() == "Cerrar Sesion" -> navToLogOut(fragment)
                txtOption.text.toString() == "Historial de compras" -> navToHistory(fragment)
            }
        }
    }

    private fun navToLogOut(fragment: Fragment){
        LocalCart.clear()
        NavHostFragment.findNavController(fragment).navigate(
                action.actionListUserFragmentToLogInFragment(true)
        )
        User.disconnectedUser()
    }

    private fun navToHistory(fragment: Fragment){
        NavHostFragment.findNavController(fragment).navigate(
                action.actionListUserFragmentToHistoryOrdersFragment()
        )
    }
}