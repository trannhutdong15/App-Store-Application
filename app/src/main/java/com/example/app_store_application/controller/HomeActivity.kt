package com.example.app_store_application.controller

import android.app.AlertDialog
import com.example.app_store_application.adapter.GameAdapter
import com.example.app_store_application.ViewModel.HomeViewModel
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

        gameAdapter = GameAdapter(emptyList(), this::onOptimizeClick, this::onEditClick,this::onDeleteClick)
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
    private fun onDeleteClick(game: GameEntity) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirm Deletion")
        alertDialogBuilder.setMessage("Are you sure you want to delete this game?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteGame(game.id) { success ->
                if (success) {
                    runOnUiThread {
                        Toast.makeText(this, "Game deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this, "Failed to delete game", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
