package my.edu.tarc.ezcharge.Inbox

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.MainActivity
import my.edu.tarc.ezcharge.PumpCharging.ChargingScanActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val extras = intent.extras

        val heading = extras!!.getString("heading")
        val imageId = extras!!.getInt("imageId")
        val news = extras!!.getString("news")
        val dateTime = extras!!.getString("dateTime")
        val continueButton = extras!!.getString("continueButton")

        binding.heading.text = heading
        binding.imageHeading.setImageResource(imageId)
        binding.news.text = news
        binding.dateTime.text = dateTime
        binding.newsButton.text = continueButton

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        binding.newsButton.setOnClickListener {
            if(continueButton == "FUEL UP NOW"){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else if(continueButton == "VERIFY NOW"){
                val intent = Intent(this, MainActivity::class.java) //change to profile activity
                startActivity(intent)
            }else if(continueButton == "VISIT"){
                //Implicit Intent - to view a web page
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100003643284691"))
                startActivity(intent)
            }else{
                //Implicit Intent - to view a web page
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("https://www.tarc.edu.my/"))
                startActivity(intent)
            }
        }
    }
}