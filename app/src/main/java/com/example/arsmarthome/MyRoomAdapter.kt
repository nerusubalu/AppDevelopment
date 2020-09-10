package com.example.arsmarthome

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyRoomAdapter (
    var context: RoomActivity,
    var img: MutableList<Int>,
    var name: MutableList<String>,
    var mail: String
): RecyclerView.Adapter<MyRoomAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): MyRoomAdapter.MyViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.room, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.i.setImageResource(img[position])
        holder.tv.text = name[position].split("_".toRegex()).map { it.trim() }[0]
        holder.card.setOnClickListener {
            val intent = Intent(context, ApplianceActivity::class.java)
            intent.putExtra("email",mail)
            intent.putExtra("room",name[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return name.size
    }

    class MyViewHolder (itemView: View):
        RecyclerView.ViewHolder(itemView) {
        var i: ImageView = itemView.findViewById(R.id.image_view)
        var tv: TextView = itemView.findViewById(R.id.text_view)
        var card: CardView = itemView.findViewById(R.id.room_card)
    }
}