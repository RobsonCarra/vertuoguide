package br.com.alura.ceep.ui.coffemachine.exceptions

class NotFoundException(msg: String = "Não encontrado") : Exception() {

  override val message: String = msg

}