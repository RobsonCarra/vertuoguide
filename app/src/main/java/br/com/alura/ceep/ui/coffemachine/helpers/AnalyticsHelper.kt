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
    const val ADD_ADDED = "addCoffeeSaved"
    const val ADD_OPENED = "addCoffeeOpened"
    const val ADD_ERROR = "addCoffeeError"
    const val ADD_CLICKED = "addCoffeeClicked"
    const val AVAILABLE_OPENED = "availableCoffeesOpened"
    const val AVAILABLE_RETURNED = "availableCoffeesReturned"
    const val AVAILABLE_SUCESS = "availableCoffeesSucess"
    const val AVAILABLE_ERROR = "availableCoffeesError"
    const val AVAILABLE_FILTERED = "availableCoffeeFiltered"
    const val AVAILABLE_CLICKED = "availableCoffeesClicked"
    const val AVAILABLE_ITEM_CLICKED = "availableCoffeesItemClicked"
    const val AVAILABLE_SEARCHED = "availableCoffeesSearched"
    const val DRINK_OPENED = "drinkNowCoffeeOpened"
    const val DRINK_RETURNED = "drinkNowCoffeeReturned"
    const val DRINK_CLICKED = "drinkNowCoffeeClicked"
    const val DRINK_DRINKED = "drinkNowCoffeeDrinked"
    const val DRINK_ERROR = "drinkNowCoffeeError"
    const val HOME_OPENED = "homeOpened"
    const val HOME_ADD_COFFEE_CLICKED = "homeAddCoffeeClicked"
    const val HOME_ITEM_CLICKED = "homeCoffeesItemClicked"
    const val HOME_SUCCESS = "homeSucess"
    const val HOME_EMPTY = "homeEmpty"
    const val HOME_ERROR = "homeError"
    const val HOME_COFFEE_DRINKED = "homeDrinked"
    const val INVENTORY_OPENED = "inventoryOpened"
    const val INVENTORY_ATT_COFFEE_CLICKED = "inventoryAttCoffeeClicked"
    const val INVENTORY_NEW_COFFEE_CLICKED = "inventoryNewCoffeeClicked"
    const val INVENTORY_ADD_COFFEE_CLICKED = "inventoryAddCoffeeClicked"
    const val INVENTORY_SUCESS = "inventorySucess"
    const val INVENTORY_FILTERED = "inventoryCoffeeFiltered"
    const val INVENTORY_ERROR_BY_USER = "inventoryErrorByUser"
    const val INVENTORY_ERROR_BY_NAME = "inventoryErrorByName"
    const val INVENTORY_CLICKED = "inventoryCoffeeSearch"
    const val INVENTORY_SEARCHED = "inventoryCoffeeFiltered"
    const val PROFILE_OPENED = "profileOpened"
    const val PROFILE_CAMERA_CLICKED = "profileCameraClicked"
    const val PROFILE_RATE_CLICKED = "profileRateClicked"
    const val PROFILE_SHARE_CLICKED = "profileShareClicked"
    const val PROFILE_TERMS_CLICKED = "profileTermsClicked"
    const val PROFILE_EXIT_CLICKED = "profileExitClicked"
    const val PROFILE_PHOTO_HANDLER = "profilePhotoHandler"
    const val PROFILE_LOAD_PHOTO = "profileLoadPhoto"
    const val REGISTER_OPENED = "registerOpened"
    const val REGISTER_GO_TO_LOGIN = "registerGoToLogin"
    const val REGISTER_RETURNED = "registerReturned"
    const val REGISTER_CREATE_ACCOUNT = "registerCreateAccount"
    const val REGISTER_CREATE_ACCOUNT_SUCESS = "registerCreateAccountSuccess"
    const val REGISTER_CREATE_ACCOUNT_ERROR = "registerCreateAccountError"
  }
}