package my.edu.tarc.ezcharge

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import my.edu.tarc.ezcharge.Location.LocationFragment
import my.edu.tarc.ezcharge.More.MoreFragment
import my.edu.tarc.ezcharge.Pay_TopUp.PayTopUpFragment
import my.edu.tarc.ezcharge.Inbox.InboxFragment
import my.edu.tarc.ezcharge.databinding.ActivityMainBinding
import my.edu.tarc.ezcharge.login.SplashActivity

private lateinit var binding : ActivityMainBinding
private const val CAMERA_REQUEST_CODE = 101
private const val FINE_LOCATION_RQ = 102
class MainActivity : AppCompatActivity() {

    private val locationFragment = LocationFragment()
    private val payTopUpFragment = PayTopUpFragment()
    private val inboxFragment = InboxFragment()
    private val moreFragment = MoreFragment()
    private var login :String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Associate view(s) to code
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_info)

        //This code below is force light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        checkForPermissions(android.Manifest.permission.CAMERA, "camera", CAMERA_REQUEST_CODE)
        checkForPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION_RQ)

        //when user open app
        //add (use intent to check whether user login)
        login = intent.getStringExtra("Login").toString()

        if(login == "null"){
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            menuNavigation()
            replaceFragment(locationFragment)
        }
    }

    //Use to replace fragment when user select different tab at bottom navigation bar
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_top_container,fragment)
            transaction.commit()
        }
    }

    private fun menuNavigation(){
        replaceFragment(locationFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(locationFragment)
                R.id.digital_pay -> replaceFragment(payTopUpFragment)
                R.id.inbox -> replaceFragment(inboxFragment)
                R.id.more -> replaceFragment(moreFragment)
            }
            true
        }
    }

    private fun checkForPermissions(permission: String, name: String, requestCode: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun innerCheck(name: String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "$name permission granted", Toast.LENGTH_SHORT).show()
            }
        }

        when (requestCode){
            FINE_LOCATION_RQ -> innerCheck("location")
            CAMERA_REQUEST_CODE -> innerCheck("camera")
        }
    }
    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}