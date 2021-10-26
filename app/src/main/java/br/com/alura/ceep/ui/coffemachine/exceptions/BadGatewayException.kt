package br.com.alura.ceep.ui.coffemachine.exceptions

class BadGatewayException(msg: String = "Problema de conexão com o gateway") : Exception() {

  override val message: String = msg

}