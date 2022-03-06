package my.edu.tarc.ezcharge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import my.edu.tarc.ezcharge.Location.LocationFragment
import my.edu.tarc.ezcharge.More.MoreFragment
import my.edu.tarc.ezcharge.Pay_TopUp.PayTopUpFragment
import my.edu.tarc.ezcharge.Rewards.RewardsFragment
import my.edu.tarc.ezcharge.databinding.ActivityMainBinding

private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val locationFragment = LocationFragment()
    private val payTopUpFragment = PayTopUpFragment()
    private val rewardsFragment = RewardsFragment()
    private val moreFragment = MoreFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Associate view(s) to code
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_info)
        setContentView(binding.root)

//        //when user open app
//        //add (use intent to check whether user login)
//        if(login == "null"){
//            val intent = Intent(this, LoginActivity::class.java)
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
                R.id.rewards -> replaceFragment(rewardsFragment)
                R.id.more -> replaceFragment(moreFragment)
            }
            true
        }
    }
}