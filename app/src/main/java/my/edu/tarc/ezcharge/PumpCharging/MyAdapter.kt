package my.edu.tarc.ezcharge.PumpCharging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.adapter.MyNewsAdapter

class MyAdapter(private val histList : ArrayList<HistoryCharge>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = histList[position]

        holder.histTypes.text = currentitem.histtypes
        holder.locationCharge.text = currentitem.location
        holder.types.text = currentitem.types
        holder.pay.text = "-RM " + currentitem.pay + "0"
        holder.timedateuser.text = currentitem.timedate
    }

    override fun getItemCount(): Int {
        return histList.size
    }

    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val histTypes : TextView = itemView.findViewById(R.id.histTypes)
        val locationCharge : TextView = itemView.findViewById(R.id.location)
        val types : TextView = itemView.findViewById(R.id.types)
        val pay : TextView = itemView.findViewById(R.id.pay)
        val timedateuser : TextView = itemView.findViewById(R.id.date)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}