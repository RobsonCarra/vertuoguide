package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import br.com.alura.ceep.ui.coffemachine.R


class ProfileFragment : Fragment() {
    private lateinit var camera: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        listeners()

    }

    private fun setup(view: View) {
        camera = view.findViewById(R.id.camera_button)
    }

    private fun listeners() {
        camera.setOnClickListener { v: View? ->

            val intent = Intent()
            intent.action = MediaStore.ACTION_IMAGE_CAPTURE
            startActivity(intent)

        }

    }
}

