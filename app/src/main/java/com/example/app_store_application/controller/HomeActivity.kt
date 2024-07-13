package com.example.app_store_application.controller

import com.example.app_store_application.adapter.GameAdapter
import HomeViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.database.GameEntity

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: HomeViewModel
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerViewRecentGames)
        recyclerView.layoutManager = LinearLayoutManager(this)

        gameAdapter = GameAdapter(emptyList(), this::onOptimizeClick, this::onEditClick)
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
                gameAdapter.updateGames(it)
            }
        }
    }

    private fun onOptimizeClick(game: GameEntity) {
        viewModel.optimizeGame(game) { success ->
            runOnUiThread {
                if (success) {
                    showToast("Optimization completed")
                } else {
                    showToast("Optimization failed")
                }
            }
        }
    }
    private fun onEditClick(game: GameEntity) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("GAME_ID", game.id)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
