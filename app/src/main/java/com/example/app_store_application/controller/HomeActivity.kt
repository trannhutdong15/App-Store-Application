package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.adapter.GameAdapter
import com.example.app_store_application.database.AppDatabase
import com.example.app_store_application.database.GameEntity
import com.example.app_store_application.databinding.ActivityHomeBinding
import com.example.app_store_application.ViewModel.HomeViewModel

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
        gameAdapter = GameAdapter(
            emptyList(),
            this::onOptimizeClick,
            this::onEditClick,
            this::onDeleteClick
        )
        recyclerView.adapter = gameAdapter

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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
                gameAdapter = GameAdapter(
                    it,
                    this::onOptimizeClick,
                    this::onEditClick,
                    this::onDeleteClick
                )
                recyclerView.adapter = gameAdapter
            }
        }
    }

    private fun onOptimizeClick(game: GameEntity) {
        // Handle optimize click (e.g., navigate to optimize screen)
        // This logic can be expanded based on your app's requirements
        // For simplicity, we're just logging the game name here
        viewModel.optimizeGame(game)
    }

    private fun onEditClick(game: GameEntity) {
        // Handle edit click (e.g., navigate to edit screen)
        // This logic can be expanded based on your app's requirements
        // For simplicity, we're just logging the game name here
        viewModel.editGame(game)
    }

    private fun onDeleteClick(game: GameEntity) {
        // Handle delete click
        viewModel.deleteGame(game)
    }
}
