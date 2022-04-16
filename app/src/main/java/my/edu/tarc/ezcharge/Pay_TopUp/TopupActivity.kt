package my.edu.tarc.ezcharge.Pay_TopUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityTopupBinding

class TopupActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTopupBinding
    private lateinit var topupSelectionItemArrayList : ArrayList<TopupSelectionItem>

    private lateinit var firebaseAuth: FirebaseAuth
    var userPass = ""
    var userUID = ""
    var userUIDFirst = ""
    var balance = 0.00

    val extras = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val extras = intent.extras
        val  balanceHold = extras!!.getDouble("balance")
        val  userIDHold = extras!!.getString("userUID").toString()

        userUID = userIDHold
        balance = balanceHold


//        firebaseAuth = FirebaseAuth.getInstance()
//        //get current user, if is logged in
//        val firebaseUser = firebaseAuth.currentUser
//
//        val ref = FirebaseDatabase.getInstance().getReference("Users")
//
//        //Get Wallet Balance
//
//        //Get USER_PIN
//        val userPin = FirebaseDatabase.getInstance().getReference("Pin")
//        if (firebaseUser != null) {
//            userUIDFirst = firebaseUser.uid
//        }
//        userPin.child(userUIDFirst).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val userPinIn = snapshot.child("pin").value
//                userPass = userPinIn.toString()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//
//        ref.child(userUIDFirst)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val userUIDPri = snapshot.child("uid").value
//                    userUID = userUIDPri.toString()
//                }
//
//                override fun onCancelled(error: DatabaseError){
//
//                }
//            })


        binding.textViewBalance.text = String.format("Balance: RM %.2f",balance)

        val imageId = intArrayOf(
            R.drawable.auto_topup_icon,R.drawable.voucher_topup_icon
        )

        val name = arrayOf(
            "Auto top-up",
            "Voucher top-up"
        )

        val message = arrayOf(
            "Top up automatically when balance falls below minimum amount",
            "Use your EzCharge voucher to top up easily"
        )

        topupSelectionItemArrayList = ArrayList()

        for(i in name.indices){
            val item = TopupSelectionItem(name[i],message[i],imageId[i])
            topupSelectionItemArrayList.add(item)
        }

        binding.listViewTopupSelection.isClickable = true
        binding.listViewTopupSelection.adapter = TopupSelectionAdapter(this,topupSelectionItemArrayList)
        binding.listViewTopupSelection.setOnItemClickListener { parent, view, position, id ->

            if(name[position]=="Auto top-up"){
                val intent = Intent(this, AutoTopupActivity::class.java)
                startActivity(intent)
            }else if(name[position]=="Voucher top-up"){
                val intent = Intent(this, VoucherTopupActivity::class.java)
                extras.putString("userUID",userUID)
                extras.putDouble("userBalance",balance)
                intent.putExtras(extras)
                startActivity(intent)
            }

        }

        binding.viewBackFrag.setOnClickListener {
            finish()
        }

        //When user click "OTHERS" button
        binding.topupBtnAmountOther.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                this, R.style.DialogStyle
            )
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.layout_topup_others_bottom_sheet,
                findViewById<LinearLayout>(R.id.topupOthersBtmSheet)
            )

            var topupAmountBtn = bottomSheetView.findViewById<Button>(R.id.buttonTopupAmountTry)
            var topupAmountText = bottomSheetView.findViewById<EditText>(R.id.editTextTopupAmountTry)



            topupAmountText.addTextChangedListener(object : TextWatcher
            {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!TextUtils.isEmpty(topupAmountText.text.toString())){
                        var amount = topupAmountText.text.toString().toDouble()
                        topupAmountBtn.isEnabled = amount in 10.00..300.00
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            topupAmountBtn.setOnClickListener{
                val cardPayOthers = Intent(this, CreditCardForm::class.java)
                val amountOthers = topupAmountText.text.toString().toDouble()
                extras.putDouble("topupAmountOthers", amountOthers)
                extras.putString("topupType","Normal Top-Up")
                extras.putString("userUID",userUID)
                extras.putDouble("userBalance",balance)
                cardPayOthers.putExtras(extras)
                startActivity(cardPayOthers)
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        binding.topupBtnAmount20.setOnClickListener{
            val cardPay1 = Intent(this, CreditCardForm::class.java)
            extras.putDouble("topupAmount", 20.00)
            extras.putString("topupType","Normal Top-Up")
            extras.putString("userUID",userUID)
            extras.putDouble("userBalance",balance)
            cardPay1.putExtras(extras)
            startActivity(cardPay1)
        }
        binding.topupBtnAmount40.setOnClickListener{
            val cardPay2 = Intent(this, CreditCardForm::class.java)
            extras.putDouble("topupAmount", 40.00)
            extras.putString("topupType","Normal Top-Up")
            extras.putString("userUID",userUID)
            extras.putDouble("userBalance",balance)
            cardPay2.putExtras(extras)
            startActivity(cardPay2)
        }
        binding.topupBtnAmount60.setOnClickListener{
            val cardPay3 = Intent(this, CreditCardForm::class.java)
            extras.putDouble("topupAmount", 60.00)
            extras.putString("topupType","Normal Top-Up")
            extras.putString("userUID",userUID)
            extras.putDouble("userBalance",balance)
            cardPay3.putExtras(extras)
            startActivity(cardPay3)
        }
        binding.topupBtnAmount80.setOnClickListener{
            val cardPay4 = Intent(this, CreditCardForm::class.java)
            extras.putDouble("topupAmount", 80.00)
            extras.putString("topupType","Normal Top-Up")
            extras.putString("userUID",userUID)
            extras.putDouble("userBalance",balance)
            cardPay4.putExtras(extras)
            startActivity(cardPay4)
        }
        binding.topupBtnAmount100.setOnClickListener{
            val cardPay5 = Intent(this, CreditCardForm::class.java)
            extras.putDouble("topupAmount", 100.00)
            extras.putString("topupType","Normal Top-Up")
            extras.putString("userUID",userUID)
            extras.putDouble("userBalance",balance)
            cardPay5.putExtras(extras)
            startActivity(cardPay5)
        }

    }

//        var actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }


//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                val parentIntent = NavUtils.getParentActivityIntent(this);
//                if (parentIntent != null) {
//                    parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
//                }
//                startActivity(parentIntent)
//                finish()
//                return true
//            }
//        }
//        return super.onContextItemSelected(item)
//    }
}