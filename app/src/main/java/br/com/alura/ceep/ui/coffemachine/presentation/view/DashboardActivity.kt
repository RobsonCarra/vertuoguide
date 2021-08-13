package br.com.alura.ceep.ui.coffemachine.presentation.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.ceep.ui.coffemachine.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

  private val nav: BottomNavigationView by lazy { findViewById(R.id.navigation) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.dashboard_activity)
    nav.setOnNavigationItemSelectedListener(this)
    supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.homeAction -> {
        nav.menu.getItem(0).isChecked = true
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
      }
      R.id.favAction -> {
        nav.menu.getItem(2).isChecked = true
        supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
      }
    }
    return false
  }
}