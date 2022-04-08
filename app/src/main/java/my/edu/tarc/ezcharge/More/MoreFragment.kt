package my.edu.tarc.ezcharge.More

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentMoreBinding
import my.edu.tarc.ezcharge.login.BeforeLoginActivity
import java.lang.Exception

class MoreFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentMoreBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //get firebase current user
    private lateinit var firebaseUser: FirebaseUser

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        //Write your fragment code below
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        //setup progress dialog
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait..")
        progressDialog.setCanceledOnTouchOutside(false)

        checkUser()
        loadUserInfo()

//        binding.accountTypeTv.text = "N/A"
//        binding.statusTv.text = "N/A"

        binding.logoutBtn.setOnClickListener{
            firebaseAuth.signOut()

            val intent = Intent (this@MoreFragment.requireContext(), BeforeLoginActivity::class.java)
            startActivity(intent)
        }

        binding.profileEditBtn.setOnClickListener{
            val intent = Intent (this@MoreFragment.requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        //verify user
        binding.statusTv.setOnClickListener {
            if(firebaseUser.isEmailVerified){
                //if user is verified
                Toast.makeText(context, "Account already verified!", Toast.LENGTH_SHORT).show()
            }else{
                //if not verified will show a confirmation dialog
                emailVerificationDialog()
            }

        }

        return binding.root
    }

    private fun emailVerificationDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Verify Email")
            .setMessage("Are you sure you want to send the verification instructions to this email ${firebaseUser.email}")
            .setPositiveButton("SEND"){d,e->
                sendEmailVerification()
            }

            .setNegativeButton("CANCEL"){d,e->
                d.dismiss()
            }
            .show()
    }

    private fun sendEmailVerification() {
        progressDialog.setMessage("Sending email verification to email ${firebaseUser.email}")
        progressDialog.show()

        //send the instructions
        firebaseUser.sendEmailVerification()
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Instructions sent to ${firebaseUser.email}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(context, "Failed to send due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {
        //check whether the user is verified or not
        if (firebaseUser.isEmailVerified){
            binding.statusTv.text = "Verified"
        }else{
            binding.statusTv.text = "Not Verified"
        }

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //get user info
                    val name = "${snapshot.child("name").value}"
                    val email = "${snapshot.child("email").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val userType = "${snapshot.child("userType").value}"

                    //set data
                    binding.nameTv.text = name
                    binding.emailTv.text = email
                    binding.accountTypeTv.text = userType

                    //set image
                    try{
                        Glide.with(this@MoreFragment)
                            .load(profileImage).placeholder(R.drawable.ic_person_gray)
                            .into(binding.profileIv)
                    }catch (e: Exception) {

                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            binding.emailTv.text = "Not Logged In"

        }else{
            val email = firebaseUser.email
            binding.emailTv.text = email
        }
    }
}