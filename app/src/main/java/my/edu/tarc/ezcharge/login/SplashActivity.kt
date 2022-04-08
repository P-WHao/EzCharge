package my.edu.tarc.ezcharge.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.R

class SplashActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed(Runnable {
            checkUser()
        }, 1000)
    }

    private fun checkUser() {
        //get current user, if is logged in
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null){
            startActivity(Intent(this, BeforeLoginActivity::class.java))
            finish()
        }else{

            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        //get usertype
                        val userType = snapshot.child("userType").value
                        if (userType == "user"){
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            intent.putExtra("Login","1")
                            startActivity(intent)
                            finish()
                        }else if (userType == "admin"){
                            intent.putExtra("Login","1")
                            startActivity(Intent(this@SplashActivity, DashboardAdminActivity::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError){

                    }
                })
        }
    }
}
/*Keep user logged in
1. Check if user is logged in
2. Check type of user
 */