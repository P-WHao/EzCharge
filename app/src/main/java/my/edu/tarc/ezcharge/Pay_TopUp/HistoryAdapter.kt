package my.edu.tarc.ezcharge.Pay_TopUp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.ezcharge.R

class HistoryAdapter(private val topupHistory : ArrayList<TopUp>) :RecyclerView.Adapter<HistoryAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.topup_history_item,
        parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = topupHistory[position]

        holder.topupType.text = currentItem.topupType
        holder.topupAmount.text = String.format("+RM %.2f", currentItem.topupAmount)
        holder.topupDate.text = currentItem.topupDate
    }

    override fun getItemCount(): Int {
        return topupHistory.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val topupType : TextView = itemView.findViewById(R.id.textViewTopupType)
        val topupAmount: TextView = itemView.findViewById(R.id.textViewTopupAmount)
        val topupDate: TextView = itemView.findViewById(R.id.textViewTopupDate)

    }

}