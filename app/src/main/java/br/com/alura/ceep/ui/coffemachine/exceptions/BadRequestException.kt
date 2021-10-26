package br.com.alura.ceep.ui.coffemachine.exceptions

class BadRequestException(val msg: String = "Requisição inválida") : Exception() {

  override val message: String = msg

}