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
    lateinit var news : Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)

        //Write your fragment code below
        imageId = arrayOf(
            R.drawable.ic_baseline_close_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24,
            R.drawable.ic_baseline_mail_24
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
            getString(R.string.title_a),
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1"
        )

        news = arrayOf(
            getString(R.string.title_a),
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1",
            "Text1"
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
                intent.putExtra("heading", newArrayList[position].heading)
                intent.putExtra("imageId", newArrayList[position].titleImage)
                intent.putExtra("news", news[position])
                startActivity(intent)
            }

        })
    }
}