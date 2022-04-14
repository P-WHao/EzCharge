package my.edu.tarc.ezcharge.PumpCharging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.ezcharge.R

class MyAdapter(private val histList : ArrayList<HistoryCharge>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = histList[position]

        holder.locationCharge.text = currentitem.location
        holder.types.text = currentitem.types
        holder.pay.text = "RM " + currentitem.pay + "0"
    }

    override fun getItemCount(): Int {
        return histList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val locationCharge : TextView = itemView.findViewById(R.id.location)
        val types : TextView = itemView.findViewById(R.id.types)
        val pay : TextView = itemView.findViewById(R.id.pay)
    }


}