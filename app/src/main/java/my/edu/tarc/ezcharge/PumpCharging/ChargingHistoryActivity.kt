package my.edu.tarc.ezcharge.PumpCharging

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.eventbus.UpdateCartEvent
import org.greenrobot.eventbus.EventBus

class ChargingHistoryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyArrayList : ArrayList<HistoryCharge>

    private var userUID = ""
//    private lateinit var firebaseAuth: FirebaseAuth
//    var userUID = ""
//    var userUIDFirst = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charging_history)

        val buttonBack: ImageButton = findViewById(R.id.backBtn)

        val currentUID = intent.getStringExtra("CURRENT_UID").toString()
        userUID = currentUID

        buttonBack.setOnClickListener{
            finish()
        }

        historyRecyclerView = findViewById(R.id.histList)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)

        historyArrayList = arrayListOf<HistoryCharge>()
        getHistoryData()

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

                    historyRecyclerView.adapter = MyAdapter(historyArrayList)
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