package br.com.alura.ceep.ui.coffemachine.presentation

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat
import androidx.fragment.app.Fragment
import br.com.alura.ceep.ui.coffemachine.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.net.URI
import java.util.jar.Manifest


class ProfileFragment : Fragment() {

    private lateinit var camera: ImageView
    private lateinit var notifications: TextView
    private lateinit var share: TextView
    private lateinit var rate: TextView
    private lateinit var terms: TextView
    private lateinit var myData: TextView
    private lateinit var exit: TextView
    private lateinit var on: TextView
    private lateinit var off: TextView
    private val REQUEST_CODE = 1
    private lateinit var mStorage: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
//       e
        listeners()
        mStorage = FirebaseStorage.getInstance().getReference()

    }

    private fun setup(view: View) {
        camera = view.findViewById(R.id.camera_button)
        notifications = view.findViewById(R.id.notifications)
        share = view.findViewById(R.id.share)
        rate = view.findViewById(R.id.rate)
        terms = view.findViewById(R.id.terms_of_use)
        myData = view.findViewById(R.id.my_data)
        exit = view.findViewById(R.id.exit)
        on = view.findViewById(R.id.on)
        off = view.findViewById(R.id.off)
    }

    private fun listeners() {

        camera.setOnClickListener { v: View? ->

            val intent = Intent()
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE
            startActivityForResult(intent, REQUEST_CODE)

        }
        on.setOnClickListener { v: View? ->
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
        }
        off.setOnClickListener { v: View? ->
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(REQUEST_CODE==1 && resultCode == RESULT_OK && data!=null){
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(c,uri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            camera.setBackgroundDrawable(bitmapDrawable)
        }
    }



//    private fun addUploadRecordToDb(uri: String){
//        val db = FirebaseFirestore.getInstance()
//        val data = HashMap<String, Any>()
//        data["imageUrl"] = uri
//
//        db.collection("posts")
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//            }
//            .addOnFailureListener { e ->
//            }
//    }

}




