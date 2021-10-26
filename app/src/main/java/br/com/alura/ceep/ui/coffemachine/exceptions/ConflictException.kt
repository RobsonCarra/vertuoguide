package br.com.alura.ceep.ui.coffemachine.exceptions

class ConflictException (msg: String = "Dados duplicados") : Exception() {

  override val message: String = msg

}