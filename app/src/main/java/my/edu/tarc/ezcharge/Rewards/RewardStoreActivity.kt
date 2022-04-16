package my.edu.tarc.ezcharge.Rewards

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityRewardStoreBinding

private lateinit var newRecylerview : RecyclerView
private lateinit var newArrayList : ArrayList<Reward_News>
lateinit var titleImage : Array<Int>
lateinit var heading : Array<String>
lateinit var news: Array<String>
lateinit var points: Array<String>
private lateinit var binding: ActivityRewardStoreBinding

private lateinit var firebaseAuth: FirebaseAuth

private lateinit var progressDialog: ProgressDialog

private lateinit var dbref : DatabaseReference

var tempUID2 = ""

class RewardStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToMain.setOnClickListener{
            onBackPressed()
        }

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        tempUID2 = firebaseAuth.uid.toString()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        loadPointsInfo()

        titleImage = arrayOf(
            R.drawable.reward2,
            R.drawable.reward1,
            R.drawable.reward3,
            R.drawable.reward4,
            R.drawable.reward5
        )
        heading = arrayOf(
            "2 Tickets of TGV Cinemas",
            "Box of 3 Assorted Dunkin Donuts",
            "RM10 Texas Chicken Voucher",
            "RM 5 llallao Voucher",
            "RM 10 Tealive Voucher"
        )

        news = arrayOf(
            "Dunkin' is an everyday stop for coffee and baked goods. Dunkin’ donuts are made fresh daily and in a variety of delicious flavours, there’s a favourite for everyone here.",
            "TGV Cinemas Sdn Bhd was the first to introduce the total-cinema concept to the ever-discerning Malaysian. TGV Cinemas defines the next generation of cinema experience and we believe in enhancing the moviegoing experience by providing choice, comfort, and convenience, as an all-in-one package.",
            "Founded in San Antonio, Texas, USA in 1952, Texas Chicken is a highly recognized brand name in Quick Service Restaurant sector and is one of the largest quick service chicken concepts in the world. Texas Chicken serves up freshly prepared, high quality, authentic American Southern-styled fare, to provide customers with value-for-money, complete meal options. The Texas Chicken menu includes flavourful chicken both Original and Spicy, crispy tenders strips, burgers and wraps, classic side dished and hand-made from scratch honey-butter biscuits.",
            "llaollao is proud to serve customers the freshest and most delicious frozen yogurt, using the finest ingredients to maintain the authenticity and health benefits of yogurt. With over 70 outlets in Malaysia, we are driven to bring our frozen delights to other parts of the nation. We have no plans to slow down and are delighted to continue serving the best quality yogurt to customers all over Asia.",
            "Tealive is one of the well-known tea brands in Southeast Asia. Its mission is to deliver a joyful experience to its customers with their tea & commit to brewing tea with the best ingredients."
        )

        points = arrayOf(
            "2200 points",
            "1300 points",
            "1050 points",
            "550 points",
            "990 points"
        )

        newRecylerview = binding.rewardActivity
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)

        newArrayList = arrayListOf<Reward_News>()
        getUserdata()

    }

    private fun loadPointsInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Reward")
        var totalCoinsText = "N/A"

        ref.child(tempUID2)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val points = "${snapshot.child("points").value}"
                    totalCoinsText = "Points: ${points}"
                    binding.rewardPoints.text = totalCoinsText
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun getUserdata() {

        for(i in titleImage.indices){

            val news = Reward_News(titleImage[i],heading[i], points[i])
            newArrayList.add(news)

        }
        var adapter = Reward_Adapter(newArrayList)
        newRecylerview.adapter = adapter
        adapter.setOnItemClickListener(object: Reward_Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@RewardStoreActivity, RewardDisplayNewsActivity::class.java)
                intent.putExtra("heading", newArrayList[position].heading)
                intent.putExtra("titleImage", newArrayList[position].titleImage)
                intent.putExtra("points", newArrayList[position].buypoints)
                intent.putExtra("news", news[position])
                startActivity(intent)
                //Toast.makeText(this@RewardStoreActivity, "You Clicked on item no. $position",Toast.LENGTH_SHORT).show()
            }

        })
    }
}