package br.com.alura.ceep.ui.coffemachine.helpers

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(): ByteArray {
  val stream = ByteArrayOutputStream()
  this.compress(Bitmap.CompressFormat.PNG, 100, stream)
  return stream.toByteArray()
}