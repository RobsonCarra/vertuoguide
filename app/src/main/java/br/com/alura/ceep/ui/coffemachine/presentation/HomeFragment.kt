package br.com.alura.ceep.ui.coffemachine.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.ceep.ui.coffemachine.R
import br.com.alura.ceep.ui.coffemachine.domain.Coffees
import br.com.alura.ceep.ui.coffemachine.helpers.CoffesRoomDataBase
import br.com.alura.ceep.ui.coffemachine.presentation.custom.CoffeAdapter
import br.com.alura.ceep.ui.coffemachine.repository.CoffesRepository
import br.com.alura.ceep.ui.coffemachine.viewmodel.CoffesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class HomeFragment() : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var coffeAdapter: CoffeAdapter = CoffeAdapter()
    private lateinit var crashButton: Button
    private lateinit var resultView: TextView

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
        val coffeesList = ArrayList<Coffees>()
        val db = FirebaseFirestore.getInstance()
        db.collection("coffees")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObjects = result.toObjects(Coffees::class.java)
                    coffeesList.addAll(myObjects)
                    Log.e(TAG,document.data.get("name").toString())
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

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

//        val coffee = Coffee(id = 10, "expresso", 10,
//            "duplo", "10", "200", "URL")
//
//        db.collection("cities").document("LA").set(coffee)


    private fun setup(view: View) {
        recyclerView = view.findViewById(R.id.coffe_list_recyclerview)
        crashButton = view.findViewById(R.id.coffe_now_button)
        resultView = view.findViewById(R.id.textViewResult)
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



