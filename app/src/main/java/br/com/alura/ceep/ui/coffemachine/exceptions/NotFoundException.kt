package br.com.alura.ceep.ui.coffemachine.exceptions

class NotFoundException(msg: String = "Usuários não encontrados") : Exception() {

  override val message: String = msg

}