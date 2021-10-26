package br.com.alura.ceep.ui.coffemachine.exceptions

class BadRequestException(val msg: String = "Falta de dados") : Exception() {

  override val message: String = msg

}