package br.com.alura.ceep.ui.coffemachine.exceptions

class InternalException(msg: String = "Erro de servidor") : Exception() {

  override val message: String = msg

}