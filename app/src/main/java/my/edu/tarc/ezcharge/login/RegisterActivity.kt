package my.edu.tarc.ezcharge.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.ezcharge.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle back button click, go back to previous screen
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        //handle click, begin register
        binding.registerBtn.setOnClickListener{
            /*Steps
            1. input data
            2. validate data
            3. create account - firebase
            4. saver user info - firebase realtime database
             */
            validateData()
        }

    }
    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        //input data
        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        val cPassword = binding.cPasswordEt.text.toString().trim()

        //Validate data
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name! ", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            Toast.makeText(this, "Invalid Email Format! ", Toast.LENGTH_SHORT).show()
        }else if (password.isEmpty()){
            //empty password
            Toast.makeText(this, "Enter your password! ", Toast.LENGTH_SHORT).show()
        }else if (cPassword.isEmpty()){
            Toast.makeText(this, "Confirm Password! ", Toast.LENGTH_SHORT).show()
        }else if (password != cPassword){
            Toast.makeText(this, "Password not match! ", Toast.LENGTH_SHORT).show()
        }else{
            createUserAccount()
        }

    }

    private fun createUserAccount() {
        //Create Account
        //show progress
        progressDialog.setMessage("Creating an Account")
        progressDialog.show()

        //Create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //account created, add user information to database
                updateUserInfo()
            }

            .addOnFailureListener{ e->
                //failed to create account
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to create account due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun updateUserInfo() {
        //Save User Info
        progressDialog.setMessage("Saving User Info")

        //timestamp
        val timestamp = System.currentTimeMillis()

        //get current user id, since user is registered
        val uid = firebaseAuth.uid


        //setup data to add inside database
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = ""
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        //set data to database
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info saved, open user dashboard
                progressDialog.dismiss()
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, GetPinActivity::class.java))
                finish()
            }

            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to save user information due to ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }
}