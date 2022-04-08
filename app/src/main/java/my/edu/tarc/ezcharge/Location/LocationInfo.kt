package my.edu.tarc.ezcharge.Location

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_location_info.*
import my.edu.tarc.ezcharge.PumpCharging.ChargingGuidelinesActivity
import my.edu.tarc.ezcharge.PumpCharging.ChargingSnackSelectorActivity
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.ActivityLocationInfoBinding

class LocationInfo : AppCompatActivity() {

    private lateinit var binding: ActivityLocationInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location_info)

        binding = ActivityLocationInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameLocation = intent.getStringExtra("name").toString()
        val nameAddress = intent.getStringExtra("address").toString()
        val imgLocation = intent.getStringExtra("image")
        val contactLocation = intent.getStringExtra("contact")
        val geoLocation = intent.getStringExtra("geo")

        binding.infoName.text = nameLocation
        binding.infoAddress.text = nameAddress
        binding.infoContact.text = contactLocation


        Picasso.get().load(imgLocation).into(infoView)


        binding.contactView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("tel:" + contactLocation)


            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this, getString(R.string.no_app),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        binding.infoView.setOnClickListener{

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:" + geoLocation)
            startActivity(intent)

        }

        binding.buttonToStation.setOnClickListener {
            val intent = Intent(this, ChargingSnackSelectorActivity::class.java)
            intent.putExtra("EXTRA_location", nameLocation)
            startActivity(intent)
        }

    }
}