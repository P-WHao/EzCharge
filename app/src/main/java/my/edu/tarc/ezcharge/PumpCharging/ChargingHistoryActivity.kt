package my.edu.tarc.ezcharge.PumpCharging

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import my.edu.tarc.ezcharge.Inbox.NewsActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.adapter.MyNewsAdapter
import my.edu.tarc.ezcharge.eventbus.UpdateCartEvent
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList

class ChargingHistoryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyArrayList : ArrayList<HistoryCharge>

    //Search
    private lateinit var tempArrayList: ArrayList<HistoryCharge>

    val extras = Bundle()

    private var userUID = ""
//    private lateinit var firebaseAuth: FirebaseAuth
//    var userUID = ""
//    var userUIDFirst = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charging_history)

        supportActionBar!!.title = getString(R.string.charge_hist)
        //val buttonBack: ImageButton = findViewById(R.id.backBtn)

        val currentUID = intent.getStringExtra("CURRENT_UID").toString()
        userUID = currentUID

//        buttonBack.setOnClickListener{
//            finish()
//        }

        historyRecyclerView = findViewById(R.id.histList)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)

        historyArrayList = arrayListOf<HistoryCharge>()
        tempArrayList = arrayListOf<HistoryCharge>()

        getHistoryData()

    }

    //Search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    historyArrayList.forEach{
                        if(it.histtypes!!.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }else if(it.location!!.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }else if(it.pay!!.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }else if(it.timedate!!.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }else if(it.types!!.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }

                    historyRecyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(historyArrayList)
                    historyRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
    private fun getHistoryData() {
        dbref = FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("ChargeHis").child(userUID)

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (historySnapshot in snapshot.children){
                        val historyCharge = historySnapshot.getValue(HistoryCharge::class.java)
                        historyArrayList.add(historyCharge!!) //ensure it not null
                    }

                    //For search
                    tempArrayList.addAll(historyArrayList)

                    historyRecyclerView.adapter = MyAdapter(historyArrayList)

                    var adapter = MyAdapter(tempArrayList)
                    historyRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            //Toast.makeText(context, "Clicked $position", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ChargingHistoryActivity, ChargingHistoryDetailsActivity::class.java)
                            extras.putString("HISTORY_TYPES", historyArrayList[position].histtypes)
                            extras.putString("LOCATIONS", historyArrayList[position].location)
                            extras.putString("CONNECT_TYPES", historyArrayList[position].types)
                            extras.putString("PAY", historyArrayList[position].pay)
                            extras.putString("TIME_DATE", historyArrayList[position].timedate)
                            intent.putExtras(extras)
                            startActivity(intent)
                        }

                    })

                }else{
                    Toast.makeText(this@ChargingHistoryActivity, getString(R.string.no_history), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}