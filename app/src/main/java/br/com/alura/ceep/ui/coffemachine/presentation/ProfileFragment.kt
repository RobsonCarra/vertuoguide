package br.com.alura.ceep.ui.coffemachine.presentation

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.alura.ceep.ui.coffemachine.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.util.*


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
    private val REQUEST_CODE = 0

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
            intent.setType("image/*")

        }
        on.setOnClickListener { v: View? ->
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
        }
        off.setOnClickListener { v: View? ->
        }
    }
//
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

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null
            && data.data != null
        ) {
            camera.setImageBitmap(data.extras!!.get("data") as Bitmap)
            val data = data.data
            data?.let {
                val storage = FirebaseStorage.getInstance().reference
                val ref: StorageReference = storage
                    .child(
                        "perfil.jpg"
                    )
                val perfilPhoto = ref.child("images/perfil.jpg")
                perfilPhoto.putFile(data)
            }
//            val storage = Firebase.storage
//
//            // [START upload_create_reference]
//            // Create a storage reference from our app
//            val storageRef = storage.reference
//
//            // Create a reference to "mountains.jpg"
//            val mountainsRef = storageRef.child("mountains.jpg")
//
//            // Create a reference to 'images/mountains.jpg'
//            val mountainImagesRef = storageRef.child("images/mountains.jpg")
//
//            // While the file names are the same, the references point to different files
//            mountainsRef.name == mountainImagesRef.name // true
//            mountainsRef.path == mountainImagesRef.path // false
//            // [END upload_create_reference]
//
//            // [START upload_memory]
//            // Get the data from an ImageView as bytes
//            camera.isDrawingCacheEnabled = true
//            camera.buildDrawingCache()
//            val bitmap = (camera.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val data1 = baos.toByteArray()
//
//            var uploadTask = mountainsRef.putBytes(data1)
//            uploadTask.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener { taskSnapshot ->
//                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//                // ...
//            }
//            // [END upload_memory]
//
//            // [START upload_stream]
//            val stream = FileInputStream(File("path/to/images/rivers.jpg"))
//
//            uploadTask = mountainsRef.putStream(stream)
//            uploadTask.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener { taskSnapshot ->
//                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//                // ...
//            }
//            // [END upload_stream]
//
//            // [START upload_file]
//            var file = Uri.fromFile(File("path/to/images/rivers.jpg"))
//            val riversRef = storageRef.child("images/${file.lastPathSegment}")
//            uploadTask = riversRef.putFile(file)
//
//            // Register observers to listen for when the download is done or if it fails
//            uploadTask.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener { taskSnapshot ->
//                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//                // ...
//            }
//            // [END upload_file]
//
//            // [START upload_with_metadata]
//            // Create file metadata including the content type
//            var metadata = storageMetadata {
//                contentType = "image/jpg"
//            }
//
//            // Upload the file and metadata
//            uploadTask = storageRef.child("images/perfil.jpg").putFile(file, metadata)
//            // [END upload_with_metadata]
//
//            // [START manage_uploads]
//            uploadTask = storageRef.child("images/perfil.jpg").putFile(file)
//
//            // Pause the upload
//            uploadTask.pause()
//            // Resume the upload
//            uploadTask.resume()
//            // Cancel the upload
//            uploadTask.cancel()
//            // [END manage_uploads]
//            // [START monitor_upload_progress]
//            // Observe state change events such as progress, pause, and resume
//            // You'll need to import com.google.firebase.storage.ktx.component1 and
//            // com.google.firebase.storage.ktx.component2
//            uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
//                val progress = (100.0 * bytesTransferred) / totalByteCount
//                Log.d(TAG, "Upload is $progress% done")
//            }.addOnPausedListener {
//                Log.d(TAG, "Upload is paused")
//            }
//            // [END monitor_upload_progress]
//
//            // [START upload_complete_example]
//            // File or Blob
//            file = Uri.fromFile(File("path/to/mountains.jpg"))
//
//            // Create the file metadata
//            metadata = storageMetadata {
//                contentType = "image/jpeg"
//            }
//
//            // Upload file and metadata to the path 'images/mountains.jpg'
//            uploadTask = storageRef.child("images/${file.lastPathSegment}").putFile(file, metadata)
//
//            // Listen for state changes, errors, and completion of the upload.
//            // You'll need to import com.google.firebase.storage.ktx.component1 and
//            // com.google.firebase.storage.ktx.component2
//            uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
//                val progress = (100.0 * bytesTransferred) / totalByteCount
//                Log.d(TAG, "Upload is $progress% done")
//            }.addOnPausedListener {
//                Log.d(TAG, "Upload is paused")
//            }.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener {
//                // Handle successful uploads on complete
//                // ...
//            }
//            // [END upload_complete_example]
//
//            // [START upload_get_download_url]
//            val ref = storageRef.child("images/mountains.jpg")
//            uploadTask = ref.putFile(file)
//
//            val urlTask = uploadTask.continueWithTask { task ->
//                if (!task.isSuccessful) {
//                    task.exception?.let {
//                        throw it
//                    }
//                }
//                ref.downloadUrl
//            }.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val downloadUri = task.result
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
//            // [END upload_get_download_url]
        }


    }
}


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!= null
//            && data.data != null) {
//            camera.setImageBitmap(data.extras!!.get("data") as Bitmap)
//            data.data?.let { data ->
//                val file_uri =  data
//                val refStorage = file_uri.getLastPathSegment()?.let {
//                    FirebaseStorage
//                        .getInstance()
//                        .reference.child("Photos").child(it)
//                }
//                refStorage?.putFile(file_uri)?.addOnSuccessListener {
//                    Toast.makeText(requireContext(), "Upload Successful!", Toast.LENGTH_SHORT)
//                        .show()
//                }?.addOnFailureListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "Upload Failed!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//    private fun uploadImageToFirebase(fileUri: Uri) {
//        val fileName = UUID.randomUUID().toString() +".jpg"
//        val refStorage = FirebaseStorage
//            .getInstance()
//            .reference
//            .child("images/$fileName")
//        refStorage.putFile(fileUri)
//            .addOnSuccessListener(
//                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
//                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//                        val imageUrl = it.toString()
//                    }
//                })
//            ?.addOnFailureListener(OnFailureListener { e ->
//                print(e.message)
//            })
//    }






