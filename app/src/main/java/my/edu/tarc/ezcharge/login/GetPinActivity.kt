package my.edu.tarc.ezcharge.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityGetPinBinding

private lateinit var binding : ActivityGetPinBinding

private lateinit var firebaseAuth: FirebaseAuth

private lateinit var progressDialog: ProgressDialog

class GetPinActivity : AppCompatActivity() {
    private var number_list = ArrayList<String>()
    private var passcode = ""
    private var num_01: String? = null
    private var num_02: String? = null
    private var num_03: String? = null
    private var num_04: String? = null
    private var num_05: String? = null
    private var num_06: String? = null

    var tempUID = ""
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityGetPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()
        tempUID = firebaseAuth.uid.toString()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btn1.setOnClickListener {
            number_list.add("1")
            passNumber(number_list)
        }
        binding.btn2.setOnClickListener {
            number_list.add("2")
            passNumber(number_list)
        }
        binding.btn3.setOnClickListener {
            number_list.add("3")
            passNumber(number_list)
        }
        binding.btn4.setOnClickListener {
            number_list.add("4")
            passNumber(number_list)
        }
        binding.btn5.setOnClickListener {
            number_list.add("5")
            passNumber(number_list)
        }
        binding.btn6.setOnClickListener {
            number_list.add("6")
            passNumber(number_list)
        }
        binding.btn7.setOnClickListener {
            number_list.add("7")
            passNumber(number_list)
        }
        binding.btn8.setOnClickListener {
            number_list.add("8")
            passNumber(number_list)
        }
        binding.btn9.setOnClickListener {
            number_list.add("9")
            passNumber(number_list)
        }
        binding.btn0.setOnClickListener {
            number_list.add("0")
            passNumber(number_list)
        }
        binding.btnClear.setOnClickListener {
            number_list.clear()
            passNumber(number_list)
        }
    }

    private fun passNumber(number_list: ArrayList<String>) {
        if (number_list.size == 0) {
            binding.dot1.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot2.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot3.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot4.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot5.setBackgroundResource(R.drawable.pin_dot_grey)
            binding.dot6.setBackgroundResource(R.drawable.pin_dot_grey)
        } else {
            when (number_list.size) {
                1 -> {
                    num_01 = number_list[0]
                    binding.dot1.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                2 -> {
                    num_02 = number_list[1]
                    binding.dot2.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                3 -> {
                    num_03 = number_list[2]
                    binding.dot3.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                4 -> {
                    num_04 = number_list[3]
                    binding.dot4.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                5 -> {
                    num_05 = number_list[4]
                    binding.dot5.setBackgroundResource(R.drawable.pin_dot_enter)
                }
                6 -> {
                    num_06 = number_list[5]
                    binding.dot6.setBackgroundResource(R.drawable.pin_dot_enter)
                    passcode = num_01 + num_02 + num_03 + num_04 + num_05 + num_06
                    addPinToFirebase()
                }
            }
        }
    }

    private fun addPinToFirebase() {
        progressDialog.show()

        val hashMap = HashMap<String, Any>()

        val timestamp = System.currentTimeMillis()


        hashMap["id"] = "$timestamp"
        hashMap["pin"] = passcode
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Pin")
        ref.child(tempUID)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Pin added successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@GetPinActivity, LoginActivity::class.java))
                finish()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}