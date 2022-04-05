package my.edu.tarc.ezcharge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import my.edu.tarc.ezcharge.Location.LocationFragment
import my.edu.tarc.ezcharge.More.MoreFragment
import my.edu.tarc.ezcharge.Pay_TopUp.PayTopUpFragment
import my.edu.tarc.ezcharge.Inbox.InboxFragment
import my.edu.tarc.ezcharge.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding

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

//        //when user open app
//        //add (use intent to check whether user login)
//        if(login == "0"){
//            val intent = Intent(this, ChargingActivity::class.java)
//            startActivity(intent)
//            //finishAffinity()
//        }else{
//            menuNavigation()
//        }
        menuNavigation()
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
}