package com.example.app_store_application.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.database.Converters
import com.example.app_store_application.database.GameEntity

class GameAdapter(
    private var games: List<GameEntity>,
    private val onOptimizeClick: (GameEntity) -> Unit,
    private val onEditClick: (GameEntity) -> Unit,
    private val onDeleteClick: (GameEntity) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
        //Optimize Button Click listener
        holder.itemView.findViewById<Button>(R.id.btnOptimize).setOnClickListener {
            onOptimizeClick(game)
        }
        //Eit Button Click listener
        holder.itemView.findViewById<ImageButton>(R.id.btnEdit).setOnClickListener {
            onEditClick(game)
        }
        //Delete button click listener
        holder.itemView.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
            onDeleteClick(game)
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGames(newGames: List<GameEntity>) {
        games = newGames
        notifyDataSetChanged()
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameName: TextView = itemView.findViewById(R.id.tvgameName)
        private val gameImage: ImageView = itemView.findViewById(R.id.ivgameImage)
        private val optimizeButton: Button = itemView.findViewById(R.id.btnOptimize)
        private val checkIcon: ImageView = itemView.findViewById(R.id.ivCheck)

        fun bind(game: GameEntity) {
            gameName.text = game.gameName

            // Convert byte array to bitmap and set to ImageView
            val bitmap = Converters().toBitmap(game.imageGame)
            gameImage.setImageBitmap(bitmap)

            // Check if the game is optimized and show check icon accordingly
            if (game.isOptimized) {
                showOptimizedIcon()
            } else {
                hideOptimizedIcon()
            }
        }

        fun showOptimizedIcon() {
            checkIcon.visibility = View.VISIBLE
            optimizeButton.setBackgroundResource(R.drawable.rounded_box_image)
        }

        fun hideOptimizedIcon() {
            checkIcon.visibility = View.GONE
            optimizeButton.setBackgroundResource(R.drawable.rounded_box_image)
        }
    }
}
