package my.edu.tarc.ezcharge.Rewards

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pay_top_up.*
import kotlinx.android.synthetic.main.fragment_rewards.*
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentRewardsBinding
import my.edu.tarc.ezcharge.login.BeforeLoginActivity
import java.util.*
import kotlin.random.Random

class RewardsFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentRewardsBinding

    var wheelimg: ImageView? = null
    var sectors = arrayOf("0", "400", "0", "300", "0", "200", "0", "100")
    var textView: TextView? = null
    var textView2: TextView? = null

    var extras = Bundle()

    var totalCoins = 0

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private lateinit var dbref : DatabaseReference

    private var userUID = ""

    var tempUID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRewardsBinding.inflate(layoutInflater)
        //Write your fragment code below

        wheelimg = binding.imageWheel
        textView = binding.infoText
        textView2 = binding.totalCoins

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        tempUID = firebaseAuth.uid.toString()

        //configure progress dialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        loadPointsInfo()

        //call spin wheel function
        binding.powerButton.setOnClickListener {
            spinWheel()
        }

        //move to reward store
        binding.rewardStore.setOnClickListener {
            val intent = Intent (this@RewardsFragment.requireContext(), RewardStoreActivity::class.java)
            startActivity(intent)
        }
        Collections.reverse(Arrays.asList(*sectors))

        return binding.root
    }

    private fun loadPointsInfo() {
        val ref = FirebaseDatabase.getInstance().getReference("Reward")
        var totalCoinsText = "N/A"

        ref.child(tempUID)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var points = snapshot.child("points").getValue(Int::class.java)
                    if(points != null){
                        totalCoins = points.toInt()
                    }else{
                        points = 0
                    }

                    totalCoinsText = "Total Points: ${points}"
                    binding.totalCoins.text = totalCoinsText
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun spinWheel() {
        val rr = java.util.Random()
        val degree = rr.nextInt(360)
        val rotateAnimation = RotateAnimation(
            0f, (degree + 720).toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = 3000
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                infoText!!.text = "Spinning..."
            }
            override fun onAnimationEnd(animation: Animation) {
                CalculatePoint(degree)
                addPointsToFirebase()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        wheelimg!!.startAnimation(rotateAnimation)
    }

    private fun addPointsToFirebase() {
        progressDialog.show()

        val hashMap = HashMap<String, Any>()

        hashMap["points"] = totalCoins
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Reward")
        ref.child(tempUID)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Rewards updated!", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun CalculatePoint(degree: Int) {
        var initialPoint = 0
        var endPoint = 45
        var i = 0
        var res: String? = null
        var prizeText = "N/A"
        var totalCoinsText = "N/A"
        do {
            if (degree > initialPoint && degree < endPoint) {
                res = sectors[i]
            }
            initialPoint += 45
            endPoint += 45
            i++
        } while (res == null)
        prizeText = "Prize is: ${res}"
        textView!!.text = prizeText
        if (res != null){
            if (res == "100"){
                totalCoins += 100
            }
            if (res == "200"){
                totalCoins += 200
            }
            if (res == "300"){
                totalCoins += 300
            }
            if (res == "400"){
                totalCoins += 400
            }
            totalCoinsText = "Total Points: ${totalCoins}"
        }
//        textView2!!.text = totalCoinsText

    }
}