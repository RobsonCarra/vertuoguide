package br.com.alura.ceep.ui.coffemachine.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

object PhotoHelper {
    fun save(
        image: ByteArray,
        fileName: String,
        storagePath: String,
        isSuccess: (success: Boolean) -> Unit
    ) {
        val bmp = BitmapFactory.decodeByteArray(image, 0, image.size)
        val storageRef = FirebaseStorage.getInstance().reference.child(storagePath)
        val spaceRef = storageRef.child(fileName)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val task = spaceRef.putBytes(data)
        task.addOnCompleteListener {
            isSuccess.invoke(it.isSuccessful)
        }
    }

    fun loadStorageImage(collection: String, image: String, loaded: (url: String) -> Unit) {
        FirebaseStorage.getInstance().reference.child(collection).child(image)
            .downloadUrl.addOnCompleteListener { complete ->
                if (complete.isSuccessful) {
                    loaded.invoke(complete.result.toString())
                    return@addOnCompleteListener
                }
                loaded.invoke("")
            }
    }
}