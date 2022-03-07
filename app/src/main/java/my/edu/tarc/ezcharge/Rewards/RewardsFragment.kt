package my.edu.tarc.ezcharge.Rewards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentRewardsBinding

class RewardsFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentRewardsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRewardsBinding.inflate(layoutInflater)
        //Write your fragment code below


        return binding.root
    }
}