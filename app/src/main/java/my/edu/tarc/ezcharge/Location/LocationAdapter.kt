package my.edu.tarc.ezcharge.Location

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.edu.tarc.ezcharge.R


class LocationAdapter(var c: Context, private val locationList : ArrayList<Location>) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_locations,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = locationList[position]

        locationList.sortBy {
            it.distance
        }

        holder.nameLocation.text = currentitem.locationName
        //holder.distanceLocation.text = currentitem.locationAddress
        holder.addressLocation.text = currentitem.locationAddress
        //Glide.with(c).load(currentitem.img).into(holder.imageLocation)
        holder.distanceLocation.text =  String.format("%.2fkm",currentitem.distance )



        holder.itemView.setOnClickListener {

            var info = Intent(c, LocationInfo::class.java)
            //intent.putExtra(EXTRA_BMI, bmi)


            info.putExtra("name", currentitem.locationName)
            info.putExtra("address", currentitem.locationAddress)
            info.putExtra("contact", currentitem.locationContact)
            info.putExtra("image", currentitem.img)
            info.putExtra("geo", currentitem.locationGeo)


            c.startActivity(info)


        }
    }
    override fun getItemCount(): Int {

        return locationList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameLocation: TextView = itemView.findViewById(R.id.locationName)
        val addressLocation: TextView = itemView.findViewById(R.id.addressLocation)
        val distanceLocation: TextView = itemView.findViewById(R.id.distanceLocation)

        // val imageLocation :ImageView = itemView.findViewById(R.id.infoView)
        //   val distanceLocation : TextView = itemView.findViewById(R.id.locationDistance)


    }


}