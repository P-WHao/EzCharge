package my.edu.tarc.ezcharge.Pay_TopUp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import my.edu.tarc.ezcharge.PumpCharging.ChargingSnackSelectorActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentPayTopUpBinding

private lateinit var firebaseAuth: FirebaseAuth

class PayTopUpFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentPayTopUpBinding
    private lateinit var dbref : DatabaseReference
    private var userUID = ""
    private var sumTotal = 0.00
    val extras = Bundle()

    var tempUID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPayTopUpBinding.inflate(layoutInflater)
        //Write your fragment code below

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        tempUID = firebaseAuth.uid.toString()
        userUID = tempUID

        dbref = FirebaseDatabase.getInstance("https://ezchargeassignment-default-rtdb.firebaseio.com/").getReference("TopUpTotal")

        dbref.child(userUID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val sum = snapshot.child("topupAmount").getValue(Double::class.java)
                    if (sum != null) {
                        sumTotal = sum
                    }
                    binding.textViewMainBalance.text = String.format("RM %.2f",sumTotal)
                    sumTotal = sumTotal
                }


//        dbref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var sum = 0.00
//                if(snapshot.exists()){
//                    for(historySnapshot in snapshot.children){
//                        sum+=historySnapshot.child("topupAmount").getValue(Double::class.java)!!
//
//                    }
//
//                }else{
//                    Toast.makeText(this@PayTopUpFragment.requireContext(), "No History Found!", Toast.LENGTH_SHORT).show()
//                }
//                binding.textViewMainBalance.text = String.format("RM%.2f",sum)
//                sumTotal = sum
//            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }


        })



        binding.buttonGoTopup.setOnClickListener{
            val intent = Intent (this@PayTopUpFragment.requireContext(),TopupActivity::class.java)
            extras.putDouble("balance", sumTotal)
            extras.putString("userUID", userUID)
            intent.putExtras(extras)
            startActivity(intent)
        }

        binding.imageViewTopupHistory.setOnClickListener {
            val intent = Intent (this@PayTopUpFragment.requireContext(),TopupHistoryActivity::class.java)
            extras.putString("userUID", userUID)
            intent.putExtras(extras)
            startActivity(intent)
        }


        return binding.root
    }


}