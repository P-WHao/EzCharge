package my.edu.tarc.ezcharge.Pay_TopUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentPayTopUpBinding

class PayTopUpFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentPayTopUpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPayTopUpBinding.inflate(layoutInflater)
        //Write your fragment code below


        return binding.root
    }
}