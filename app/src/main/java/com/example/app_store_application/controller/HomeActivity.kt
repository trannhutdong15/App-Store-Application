package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.ViewModel.HomeViewModel
import com.example.app_store_application.adapter.GameAdapter
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerViewRecentGames)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(emptyList()) // Initialize with empty list
        recyclerView.adapter = gameAdapter


        viewModel = HomeViewModel(AppDatabase.getInstance(this))

        observeViewModel()

        val addButton: Button = findViewById(R.id.btnbuttonAdd)
        addButton.setOnClickListener {
            navigateToAddGame()
        }
    }

    private fun navigateToAddGame() {
        val intent = Intent(this, AddGameActivity::class.java)
        startActivity(intent)
    }

    private fun observeViewModel() {
        viewModel.games.observe(this) { games ->
            games?.let {
                gameAdapter = GameAdapter(it)
                recyclerView.adapter = gameAdapter
            }
        }
    }
}


