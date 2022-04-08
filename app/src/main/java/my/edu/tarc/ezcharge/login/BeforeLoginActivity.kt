package my.edu.tarc.ezcharge.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.ezcharge.databinding.ActivityBeforeLoginBinding

class BeforeLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeforeLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeforeLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.skipBtn.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}