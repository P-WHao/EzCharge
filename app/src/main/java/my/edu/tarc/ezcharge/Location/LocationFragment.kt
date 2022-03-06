package my.edu.tarc.ezcharge.Location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.ezcharge.Charging.ChargingGuidelinesActivity
import my.edu.tarc.ezcharge.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLocationBinding.inflate(layoutInflater)

        binding.buttonStartCharge.setOnClickListener{
            val intent= Intent (this@LocationFragment.requireContext(), ChargingGuidelinesActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}

//or

//package my.edu.tarc.ezcharge.Location
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import my.edu.tarc.ezcharge.Charging.ChargingGuidelinesActivity
//import my.edu.tarc.ezcharge.R
//
//class LocationFragment : Fragment() {
//    private var ctx: Context? = null
//    private var self: View? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        ctx = container?.context
//        self = LayoutInflater.from(ctx).inflate(R.layout.fragment_location, container, false)
//
//        val buttonStartCharge = self?.findViewById<Button>(R.id.buttonStartCharge)
//
//        buttonStartCharge?.setOnClickListener{
//            val intent = Intent(ctx, ChargingGuidelinesActivity::class.java)
//            startActivity(intent)
//        }
//        return self
//    }
//}