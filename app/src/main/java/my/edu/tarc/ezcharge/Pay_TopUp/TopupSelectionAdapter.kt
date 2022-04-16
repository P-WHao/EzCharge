package my.edu.tarc.ezcharge.Pay_TopUp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import my.edu.tarc.ezcharge.R

class TopupSelectionAdapter(private val context: Activity, private val arrayList: ArrayList<TopupSelectionItem>) :
    ArrayAdapter<TopupSelectionItem>(context, R.layout.top_up_list_item,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.top_up_list_item,null)

        val topupSelectionIcon : ImageView = view.findViewById(R.id.topup_selection_icon)
        val topupSelectionName : TextView = view.findViewById(R.id.topup_selection_name)
        val topupSelectionMessage : TextView = view.findViewById(R.id.topup_selection_message)

        topupSelectionIcon.setImageResource(arrayList[position].imageId)
        topupSelectionName.text = arrayList[position].name
        topupSelectionMessage.text = arrayList[position].message

        return view
    }
}