package com.example.app_store_application.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.adapter.GameAdapter
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
        gameAdapter = GameAdapter(emptyList(), this::onOptimizeClick, this::onEditClick, this::onDeleteClick)
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
                gameAdapter = GameAdapter(it, this::onOptimizeClick, this::onEditClick, this::onDeleteClick)
                recyclerView.adapter = gameAdapter
            }
        }
    }


    private fun onOptimizeClick(game: GameEntity) {
        viewModel.optimizeGame(game, viewModel.viewModelScope)
    }

    private fun onEditClick(game: GameEntity) {
        val intent = Intent(this, EditGameActivity::class.java)
        intent.putExtra("game_entity", game) // Pass the GameEntity object to EditGameActivity
        startActivity(intent)
    }

    private fun onDeleteClick(game: GameEntity) {
        viewModel.deleteGame(game)
    }
}
