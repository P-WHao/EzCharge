package my.edu.tarc.ezcharge.More

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    //binding
    private lateinit var binding : FragmentMoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)
        //Write your fragment code below


        return binding.root
    }
}