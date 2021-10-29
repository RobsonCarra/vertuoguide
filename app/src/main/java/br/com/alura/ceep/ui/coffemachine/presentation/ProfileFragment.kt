package br.com.alura.ceep.ui.coffemachine.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.PhotoHelper
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.helpers.toByteArray
import br.com.alura.ceep.ui.coffemachine.presentation.Login.view.LoginActivity

class ProfileFragment : Fragment() {

  private lateinit var camera: ImageView
  private lateinit var notifications: TextView
  private lateinit var share: TextView
  private lateinit var rate: TextView
  private lateinit var terms: TextView
  private lateinit var exit: TextView
  private lateinit var on: TextView
  private lateinit var off: TextView
  private val REQUEST_CODE_PHOTO = 10

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_profile, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setup(view)
    listeners()
  }

  private fun setup(view: View) {
    camera = view.findViewById(R.id.camera_button)
    notifications = view.findViewById(R.id.notifications)
    share = view.findViewById(R.id.share)
    rate = view.findViewById(R.id.rate)
    terms = view.findViewById(R.id.terms_of_use)
    exit = view.findViewById(R.id.exit)
    on = view.findViewById(R.id.on)
    off = view.findViewById(R.id.off)
  }

  private fun listeners() {
    camera.setOnClickListener { v: View? ->
      val intent = Intent()
      intent.action = MediaStore.ACTION_IMAGE_CAPTURE
      startActivityForResult(intent, REQUEST_CODE_PHOTO)
      intent.type = "image/*"
    }
    on.setOnClickListener { v: View? ->
      Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
    }
    off.setOnClickListener { v: View? ->
    }
    exit.setOnClickListener {
      SharedPref(requireContext()).clear()
      val intent = Intent(context, LoginActivity::class.java)
      context?.startActivity(intent)
    }
  }

  @Override
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (resultCode) {
      RESULT_OK -> data?.extras?.let { response -> photoHandler(response) }
      RESULT_CANCELED -> Toast.makeText(requireContext(), "Canceled", Toast.LENGTH_SHORT)
        .show()
    }
  }

  private fun photoHandler(data: Bundle) {
    val bitmap = data.get("data") as Bitmap
    camera.setImageBitmap(bitmap)
    PhotoHelper.save(
      image = bitmap.toByteArray(),
      fileName = "perfil.jpg",
      storagePath = "users/photos",
      isSuccess = {
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
      }
    )
  }
}



