package my.edu.tarc.ezcharge.Inbox

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.ezcharge.Data.Entity.News
import my.edu.tarc.ezcharge.R
import my.edu.tarc.ezcharge.adapter.MyNewsAdapter
import my.edu.tarc.ezcharge.databinding.FragmentInboxBinding

class InboxFragment : Fragment() {
    //binding
    private var _binding: FragmentInboxBinding? = null
    private val binding get() = _binding!!

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var newArrayList: ArrayList<News>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var notes : Array<String>

    //Into next activity
    lateinit var news : Array<String>
    lateinit var newHeading : Array<String>
    lateinit var postDateTime : Array<String>
    lateinit var continueButton : Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)

        //Write your fragment code below
        imageId = arrayOf(
            R.drawable.recharging,
            R.drawable.reloadcash,
            R.drawable.fivecash,
            R.drawable.share,
            R.drawable.recharging,
            R.drawable.secure,
            R.drawable.reloadgrab,
            R.drawable.drop,
            R.drawable.recharging,
            R.drawable.newpage
        )

        heading = arrayOf(
            getString(R.string.title_a),
            getString(R.string.title_b),
            getString(R.string.title_c),
            getString(R.string.title_d),
            getString(R.string.title_e),
            getString(R.string.title_f),
            getString(R.string.title_g),
            getString(R.string.title_h),
            getString(R.string.title_i),
            getString(R.string.title_j)
        )

        notes = arrayOf(
            getString(R.string.note_a),
            getString(R.string.note_b),
            getString(R.string.note_c),
            getString(R.string.note_d),
            getString(R.string.note_e),
            getString(R.string.note_f),
            getString(R.string.note_g),
            getString(R.string.note_h),
            getString(R.string.note_i),
            getString(R.string.note_j)
        )

        newHeading = arrayOf(
            getString(R.string.inTitle_a),
            getString(R.string.inTitle_b),
            getString(R.string.inTitle_c),
            getString(R.string.inTitle_d),
            getString(R.string.inTitle_e),
            getString(R.string.inTitle_f),
            getString(R.string.inTitle_g),
            getString(R.string.inTitle_h),
            getString(R.string.inTitle_i),
            getString(R.string.inTitle_j)
        )

        news = arrayOf(
            getString(R.string.news_a),
            getString(R.string.news_b),
            getString(R.string.news_c),
            getString(R.string.news_d),
            getString(R.string.news_e),
            getString(R.string.news_f),
            getString(R.string.news_g),
            getString(R.string.news_h),
            getString(R.string.news_i),
            getString(R.string.news_j)
        )

        postDateTime = arrayOf(
            getString(R.string.dateTime_a),
            getString(R.string.dateTime_b),
            getString(R.string.dateTime_c),
            getString(R.string.dateTime_d),
            getString(R.string.dateTime_e),
            getString(R.string.dateTime_f),
            getString(R.string.dateTime_g),
            getString(R.string.dateTime_h),
            getString(R.string.dateTime_i),
            getString(R.string.dateTime_j)
        )

        continueButton = arrayOf(
            getString(R.string.button_a),
            getString(R.string.button_b),
            getString(R.string.button_c),
            getString(R.string.button_d),
            getString(R.string.button_e),
            getString(R.string.button_f),
            getString(R.string.button_g),
            getString(R.string.button_h),
            getString(R.string.button_i),
            getString(R.string.button_j)
        )

        newRecyclerview = binding.recyclerView
        newRecyclerview.layoutManager = LinearLayoutManager(context)
        newRecyclerview.setHasFixedSize(true)

        newArrayList = arrayListOf<News>()
        getUserdata()

        return binding.root
    }

    private fun getUserdata(){
        for (i in imageId.indices){
            val news = News(imageId[i], heading[i], notes[i])
            newArrayList.add(news)
        }

        var adapter = MyNewsAdapter(newArrayList)
        newRecyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyNewsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(context, "Clicked $position", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@InboxFragment.requireContext(), NewsActivity::class.java)
                intent.putExtra("heading", newHeading[position])
                intent.putExtra("imageId", newArrayList[position].titleImage)
                intent.putExtra("news", news[position])
                intent.putExtra("dateTime", postDateTime[position])
                intent.putExtra("continueButton", continueButton[position])
                startActivity(intent)
            }

        })
    }
}