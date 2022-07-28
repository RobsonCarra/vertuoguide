package br.com.alura.ceep.ui.coffemachine.presentation

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.alura.ceep.ui.coffemachine.BuildConfig
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.helpers.AnalyticsHelper
import br.com.alura.ceep.ui.coffemachine.helpers.PhotoHelper
import br.com.alura.ceep.ui.coffemachine.helpers.SharedPref
import br.com.alura.ceep.ui.coffemachine.helpers.toByteArray
import br.com.alura.ceep.ui.coffemachine.presentation.Login.view.LoginActivity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

  private lateinit var camera: ImageView
  private lateinit var notifications: TextView
  private lateinit var share: TextView
  private lateinit var rate: TextView
  private lateinit var terms: TextView
  private lateinit var exit: TextView
  private lateinit var todolist:TextView
  private lateinit var progressBar: ProgressBar
  private val REQUEST_CODE_PHOTO = 10
  private val analyticsHelper: AnalyticsHelper by lazy {
    AnalyticsHelper(requireContext())
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_profile, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setup(view)
    cameraInvisible()
    loadPerfilPhoto()
    listeners()
    analyticsHelper.log(AnalyticsHelper.PROFILE_OPENED)
  }

  private fun setup(view: View) {
    camera = view.findViewById(R.id.camera_button)
    notifications = view.findViewById(R.id.notifications)
    share = view.findViewById(R.id.share)
    rate = view.findViewById(R.id.rate)
    terms = view.findViewById(R.id.terms_of_use)
    exit = view.findViewById(R.id.exit)
    progressBar = view.findViewById(R.id.progress_bar_perfil_fragment)
    todolist = view.findViewById(R.id.to_do_list_button)
  }

  private fun listeners() {
    camera.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.PROFILE_CAMERA_CLICKED)
      val intent = Intent()
      intent.action = MediaStore.ACTION_IMAGE_CAPTURE
      startActivityForResult(intent, REQUEST_CODE_PHOTO)
      intent.type = "image/*"
    }
    rate.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.PROFILE_RATE_CLICKED)
      val reviewManager = ReviewManagerFactory.create(requireContext())
      val requestReviewTask = reviewManager.requestReviewFlow()
      requestReviewTask.addOnCompleteListener { request ->
        if (request.isSuccessful) {
          // Request succeeded and a ReviewInfo instance was received
          val reviewInfo: ReviewInfo = request.result
          val launchReviewTask: Task<*> =
            reviewManager.launchReviewFlow(requireActivity(), reviewInfo)
          launchReviewTask.addOnCompleteListener { _ ->
            Toast.makeText(requireContext(), "Obrigado pela sua avaliação", Toast.LENGTH_SHORT)
              .show()
          }
        } else {
          // Request failed
        }
      }
    }

    todolist.setOnClickListener {
      SharedPref(requireContext()).clear()
      val intent = Intent(context, ExperienceCoffee::class.java)
      context?.startActivity(intent)
    }

    share.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.PROFILE_SHARE_CLICKED)
      val sendIntent = Intent()
      sendIntent.action = Intent.ACTION_SEND
      sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
      )
      sendIntent.type = "text/plain"
      startActivity(sendIntent)
    }

    terms.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.PROFILE_TERMS_CLICKED)
      val url = "http://www.google.com"
      val i = Intent(Intent.ACTION_VIEW)
      i.data = Uri.parse(url)
      startActivity(i)
    }

    exit.setOnClickListener {
      analyticsHelper.log(AnalyticsHelper.PROFILE_EXIT_CLICKED)
      SharedPref(requireContext()).clear()
      val intent = Intent(context, LoginActivity::class.java)
      context?.startActivity(intent)
    }
  }

  fun photoLoader() {
  }

  @Override
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (resultCode) {
      RESULT_OK -> data?.extras?.let { response -> photoHandler(response) }
      RESULT_CANCELED -> Toast.makeText(requireContext(), CANCELED, Toast.LENGTH_SHORT)
        .show()
    }
  }

  private fun photoHandler(data: Bundle) {
    analyticsHelper.log(AnalyticsHelper.PROFILE_PHOTO_HANDLER)
    val bitmap = data.get(DATA) as Bitmap
    camera.setImageBitmap(bitmap)
    PhotoHelper.save(
      image = bitmap.toByteArray(),
      fileName = JPG,
      storagePath = COLLECTION,
      isSuccess = {
        Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
      }
    )
  }

  private fun loadPerfilPhoto() {
    analyticsHelper.log(AnalyticsHelper.PROFILE_LOAD_PHOTO)
    progressBar.visibility = View.VISIBLE
    PhotoHelper.loadStorageImage(COLLECTION, JPG, loaded = { image ->
      if (image != "") {
        Picasso.get().load(image)
          .into(camera)
      }
      progressBar.visibility = View.GONE
      camera.visibility = View.VISIBLE
    })
  }

  companion object {
    const val JPG = "perfil.jpg"
    const val COLLECTION = "users/photos"
    const val CANCELED = "canceled"
    const val DATA = "data"
  }
  fun cameraInvisible() {
    camera.visibility = View.GONE
  }
}



