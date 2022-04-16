package my.edu.tarc.ezcharge.Pay_TopUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityAutoTopupBinding

class AutoTopupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAutoTopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutoTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewBackTopUp1.setOnClickListener {
            finish()
        }
    }
}