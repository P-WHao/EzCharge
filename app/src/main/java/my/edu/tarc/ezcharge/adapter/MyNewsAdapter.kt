package my.edu.tarc.ezcharge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.ezcharge.Data.Entity.News
import my.edu.tarc.ezcharge.R

class MyNewsAdapter(private val newList: ArrayList<News>): RecyclerView.Adapter<MyNewsAdapter.MyNewsViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_news, parent, false)
        return MyNewsViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyNewsViewHolder, position: Int) {
        val currentItem = newList[position]
        //holder.titleImage.setImageResource(currentItem.titleImage)
        holder.notes.text = currentItem.notes
        holder.tvHeading.text = currentItem.heading
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    class MyNewsViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading : TextView = itemView.findViewById(R.id.tvHeading)
        val notes : TextView = itemView.findViewById(R.id.tvNote)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}