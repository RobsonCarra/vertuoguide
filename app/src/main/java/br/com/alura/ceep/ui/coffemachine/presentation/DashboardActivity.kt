package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import br.com.alura.ceep.ui.coffemachine.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val nav: BottomNavigationView by lazy {
        findViewById(R.id.navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        nav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
        testing()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeAction -> {
                nav.menu.getItem(0).isChecked = true
                supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment())
                    .commit()
            }
            R.id.favAction -> {
                nav.menu.getItem(1).isChecked = true
                supportFragmentManager.beginTransaction().replace(R.id.frame, FavoriteFragment())
                    .commit()
            }
            R.id.setAction -> {
                nav.menu.getItem(2).isChecked = true
                supportFragmentManager.beginTransaction().replace(
                    R.id.frame,
                    InventoryFragment()
                ).commit()
            }
            R.id.profAction -> {
                nav.menu.getItem(3).isChecked = true
                supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment())
                    .commit()
            }
        }
        return false;
    }

    private fun testing() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            if (token != null) {
                Log.d(TAG, token)
            }
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }
}
