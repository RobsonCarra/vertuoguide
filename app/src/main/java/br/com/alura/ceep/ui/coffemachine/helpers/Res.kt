package br.com.alura.ceep.ui.coffemachine.helpers

sealed class Res {
  class Success(val items: Any?) : Res()
  class Failure(val exception: Exception) : Res()
}