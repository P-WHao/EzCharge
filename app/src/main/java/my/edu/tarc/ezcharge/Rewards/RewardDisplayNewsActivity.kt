package my.edu.tarc.ezcharge.Rewards

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.databinding.ActivityRewardDisplayNewsBinding
import org.w3c.dom.Text
import java.util.HashMap

private lateinit var binding: ActivityRewardDisplayNewsBinding

private lateinit var firebaseAuth: FirebaseAuth

private lateinit var progressDialog: ProgressDialog

private lateinit var dbref : DatabaseReference

var tempUID = ""

val reward1 = 2200
val reward2 = 1300
val reward3 = 1050
val reward4 = 550
val reward5 = 990

var totalCoins = 0

class RewardDisplayNewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardDisplayNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        tempUID = firebaseAuth.uid.toString()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        val headingNews: TextView = binding.rewHeading
        val mainNews: TextView = binding.rewardDes
        val imageNews: ImageView = binding.imageHeading
        val points: TextView = binding.buyPoints

        val bundle: Bundle?= intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("titleImage")
        val news = bundle.getString("news")
        val buypoints = bundle.getString("points")



        headingNews.text = heading
        mainNews.text = news
        imageNews.setImageResource(imageId)
        points.text = buypoints

        val buyButton = binding.rewardBuy.text

        //back button
        binding.imageViewBack.setOnClickListener {
            onBackPressed()
        }
        binding.rewardBuy.setOnClickListener{
            loadPointsBalance()
            if(buyButton == "Redeem Now"){
                if(buypoints == "2200 points"){
                    if (totalCoins >= reward1){
                        totalCoins -= reward1
                        updatePointsToFirebase()
                        Toast.makeText(this,"Redeemed Successfully!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RewardDisplayNewsActivity, "Points Not Enough!",Toast.LENGTH_SHORT).show()                    }
                }

                if(buypoints == "1300 points"){
                    if (totalCoins >= reward2){
                        totalCoins -= reward2
                        updatePointsToFirebase()
                        Toast.makeText(this,"Redeemed Successfully!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RewardDisplayNewsActivity, "Points Not Enough!",Toast.LENGTH_SHORT).show()                    }
                }

                if(buypoints == "1050 points"){
                    if (totalCoins >= reward3){
                        totalCoins -= reward3
                        updatePointsToFirebase()
                        Toast.makeText(this,"Redeemed Successfully!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RewardDisplayNewsActivity, "Points Not Enough!",Toast.LENGTH_SHORT).show()                    }
                }

                if(buypoints == "550 points"){
                    if (totalCoins >= reward4){
                        totalCoins -= reward4
                        updatePointsToFirebase()
                        Toast.makeText(this,"Redeemed Successfully!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RewardDisplayNewsActivity, "Points Not Enough!",Toast.LENGTH_SHORT).show()                    }
                }

                if(buypoints == "990 points"){
                    if (totalCoins >= reward5){
                        totalCoins -= reward5
                        updatePointsToFirebase()
                        Toast.makeText(this,"Redeemed Successfully!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@RewardDisplayNewsActivity, "Points Not Enough!",Toast.LENGTH_SHORT).show()                    }
                }
            }
        }


    }

    private fun updatePointsToFirebase() {
        progressDialog.show()

        val hashMap = HashMap<String, Any>()

        hashMap["points"] = totalCoins
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Reward")
        ref.child(tempUID)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadPointsBalance() {
        val ref = FirebaseDatabase.getInstance().getReference("Reward")

        ref.child(tempUID)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val points = "${snapshot.child("points").value}"
                    totalCoins = points.toInt()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }


}