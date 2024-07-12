package com.example.app_store_application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_store_application.R
import com.example.app_store_application.database.Converters
import com.example.app_store_application.database.GameEntity


//Set up GameAdapter for recycle View progress
class GameAdapter(private val games: List<GameEntity>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }


    //get size of the game item
    override fun getItemCount(): Int = games.size

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameImage: ImageView = itemView.findViewById(R.id.ivgameImage)
        private val gameName: TextView = itemView.findViewById(R.id.tvgameName)

        fun bind(game: GameEntity) {
            gameName.text = game.gameName

            // Convert byte array to bitmap and set to ImageView
            val bitmap = Converters().toBitmap(game.imageGame)
            gameImage.setImageBitmap(bitmap)
        }
    }
}
