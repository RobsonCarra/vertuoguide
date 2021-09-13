package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
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
        myData = view.findViewById(R.id.my_data)
        exit = view.findViewById(R.id.exit)
        on = view.findViewById(R.id.on)
        off = view.findViewById(R.id.off)
    }

    private fun listeners() {
        camera.setOnClickListener { v: View? ->

            val intent = Intent()
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE
            startActivity(intent)

        }
        on.setOnClickListener { v: View? ->
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
        }
        off.setOnClickListener { v: View? ->
        }
    }
}




