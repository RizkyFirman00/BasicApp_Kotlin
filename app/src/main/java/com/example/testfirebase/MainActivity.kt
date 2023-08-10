package com.example.testfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testfirebase.databinding.ActivityMainBinding
import com.example.testfirebase.model.Orang
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val orangList = mutableListOf<Orang>()
    private val db = Firebase.firestore
    private lateinit var orangAdapter: OrangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        orangAdapter = OrangAdapter(orangList)
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.adapter = orangAdapter

        db.collection("User")
            .get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                for (document in querySnapshot.documents) {
                    val nama = document.getString("nama") ?: ""
                    val umur = document.getLong("umur")?.toInt() ?: 0
                    val email = document.getString("email") ?: ""
                    val orang = Orang(nama, umur, email)
                    orangList.add(orang)
                }
                orangAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

        binding.btnSimpan.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val umur = binding.etUmur.text.toString().toInt()
            val email = binding.etEmail.text.toString()
            val orang = Orang(nama, umur, email)
            if (nama.isNotEmpty() && umur != 0 && email.isNotEmpty()) {
                db.collection("User").add(orang)
                orangList.add(orang)
                orangAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            binding.apply {
                etNama.text?.clear()
                etUmur.text?.clear()
                etEmail.text?.clear()
            }
        }
    }
}