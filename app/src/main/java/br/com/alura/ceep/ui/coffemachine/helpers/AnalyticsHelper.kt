package br.com.alura.ceep.ui.coffemachine.helpers

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsHelper(val context: Context) {

  fun log(event: String) {
    val bundle = Bundle()
    bundle.putString("param", event)
    FirebaseAnalytics.getInstance(context).logEvent(event, bundle)
  }

  companion object {
    const val LOGIN_OPENED = "loginOpened"
    const val LOGIN_LOGGED = "loginLogged"
    const val LOGIN_CLICKED = "loginClicked"
    const val LOGIN_REGISTER = "loginRegister"
    const val LOGIN_ERROR = "loginError"
    const val LOGIN_SUCCESS = "loginSuccess"
  }
}