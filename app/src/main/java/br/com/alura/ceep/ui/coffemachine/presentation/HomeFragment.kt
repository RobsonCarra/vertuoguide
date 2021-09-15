package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffee
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var coffeAdapter: CoffeAdapter = CoffeAdapter()
    private lateinit var crashButton: Button

    private val viewModel: CoffesViewModel by viewModels {
        CoffesViewModel.CoffesViewModelFactory(
            CoffesRepository(
                CoffesRoomDataBase.getDatabase(requireContext()).coffesDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        initList()
        listeners()
        observers()
        load()
        lifecycleScope.launch {
            viewModel.getAll()
        }
    }

    private fun listeners() {

        val db = Firebase.firestore
        // Update one field, creating the document if it does not already exist.
//        val data = hashMapOf("capital" to true)
//
//        db.collection("cities").document("BJ")
//            .set(data, SetOptions.merge())
//        val city = hashMapOf(
//            "name" to "Los Angeles",
//            "state" to "CA",
//            "country" to "USA"
//        )
//
//        db.collection("cities").document("LA")
//            .set(city)
//            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

//        var database = FirebaseDatabase.getInstance().reference
//        database.setValue("Itajaí")

        val coffee = Coffee(id = 10, "expresso", 10,
            "duplo", "10", "200", "URL")

        db.collection("cities").document("LA").set(coffee)
    }


    private fun setup(view: View) {
        recyclerView = view.findViewById(R.id.coffe_list_recyclerview)
        crashButton = view.findViewById(R.id.coffe_now_button)
    }

    private fun observers() {
//        viewModel.added.observe(viewLifecycleOwner) { coffee ->
//            viewModel.getAll()
//        }
        viewModel.list.observe(viewLifecycleOwner) { coffee ->
            coffeAdapter.list.addAll(coffee)
            coffeAdapter.notifyDataSetChanged()
        }
    }

    fun initList() {
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = coffeAdapter
    }

    private fun load() {
    }
}
