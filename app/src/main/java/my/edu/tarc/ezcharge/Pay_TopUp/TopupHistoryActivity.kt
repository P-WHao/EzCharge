package my.edu.tarc.ezcharge.Pay_TopUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_topup_history.*
import my.edu.tarc.ezcharge.R

class TopupHistoryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var  topupHistoryRecyclerView: RecyclerView
    private lateinit var topupHistoryArrayList: ArrayList<TopUp>
    private var userUID = ""
    private var tryData = 0.00
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent = intent
        val extras = intent.extras
        val  userIDHold = extras!!.getString("userUID").toString()
        userUID = userIDHold

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup_history)

        topupHistoryRecyclerView = findViewById(R.id.topupHistoryList)
        topupHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        topupHistoryRecyclerView.setHasFixedSize(true)

        topupHistoryArrayList = arrayListOf<TopUp>()

        val viewBackMain = findViewById<ImageButton>(R.id.viewBackMain)
        viewBackMain.setOnClickListener {
            finish()
        }

        getUserData()

    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("TopUp").child(userUID)

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    var sum = 0.00
                    for(historySnapshot in snapshot.children){
//                        sum+=historySnapshot.child("topupAmount").getValue(Double::class.java)!!
                        val history = historySnapshot.getValue(TopUp::class.java)
                        topupHistoryArrayList.add(history!!)

                    }

                    topupHistoryRecyclerView.adapter = HistoryAdapter(topupHistoryArrayList)
                }else{
                    Toast.makeText(this@TopupHistoryActivity, "No History Found!", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }


        })
    }
}