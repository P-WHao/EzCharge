package my.edu.tarc.ezcharge.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.Location.Location
import my.edu.tarc.ezcharge.Location.LocationAdapter
import my.edu.tarc.ezcharge.databinding.ActivityLocationListBinding


class LocationListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Location>

    private lateinit var binding: ActivityLocationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_location_list)

        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//
        //userRecyclerview = findViewById(R.id.activityLocation)
        userRecyclerview = binding.activityLocation
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<Location>()
        getUserData()

        binding.backToMain.setOnClickListener {
            finish()
        }
    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Location")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(Location::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = LocationAdapter(this@LocationListActivity,userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}