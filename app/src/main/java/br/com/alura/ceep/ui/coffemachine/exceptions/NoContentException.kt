package br.com.alura.ceep.ui.coffemachine.exceptions

class NoContentException(msg: String = "Sem dados encontrados") : Exception() {

  override val message: String = msg

}