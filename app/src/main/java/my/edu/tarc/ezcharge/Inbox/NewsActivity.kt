package my.edu.tarc.ezcharge.Inbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.heading.text = heading
        binding.imageHeading.setImageResource(imageId)
        binding.news.text = news
    }
}