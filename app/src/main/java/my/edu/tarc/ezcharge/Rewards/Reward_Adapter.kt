package my.edu.tarc.ezcharge.Rewards

import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.ezcharge.R

class Reward_Adapter (private val rewardsList: ArrayList<Reward_News>) : RecyclerView.Adapter<Reward_Adapter.MyViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reward_list,
            parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = rewardsList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
        holder.pointsHeading.text = currentItem.buypoints
    }

    override fun getItemCount(): Int {
        return rewardsList.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.reward_image)
        val tvHeading : TextView = itemView.findViewById(R.id.reward_heading)
        val pointsHeading: TextView = itemView.findViewById(R.id.buy_points)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}