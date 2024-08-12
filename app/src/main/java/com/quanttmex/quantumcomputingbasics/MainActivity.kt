package com.quanttmex.quantumcomputingbasics

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var topiclist: ArrayList<TopicData>
    private lateinit var imageList: Array<Int>
    private lateinit var TitleList: ArrayList<String>
    private lateinit var search: SearchView
    private lateinit var searchList: ArrayList<TopicData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        imageList = arrayOf(
            R.drawable.ic_principles,
            R.drawable.ic_gates,
            R.drawable.ic_algorithms,
            R.drawable.ic_supremacy,
            R.drawable.ic_applications,
            R.drawable.ic_apps_quant_comp,
            R.drawable.ic_advantages,
            R.drawable.ic_disadvantages,
            R.drawable.ic_compare,
            R.drawable.ic_references
        )

        TitleList = arrayListOf(
            "INTRODUCTION TO QUANTUM COMPUTING",
            "PRINCIPLES OF QUANTUM COMPUTING",
            "QUANTUM GATES",
            "QUANTUM ALGORITHMS",
            "QUANTUM SUPREMACY EXPERIMENTS",
            "APPLICATIONS OF QUANTUM SUPREMACY",
            "APPLICATIONS OF QUANTUM COMPUTING",
            "ADVANTAGES OF QUANTUM COMPUTING",
            "DISADVANTAGES OF QUANTUM COMPUTING",
            "COMPARISON BETWEEN QUANTUM AND TRADITIONAL"
        )

        recyclerView = findViewById(R.id.recycler_view)
        search = findViewById(R.id.search_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        topiclist = arrayListOf()
        searchList = arrayListOf()
        getData()

        search.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                search.clearFocus()
                hideKeyboard(search)
            }
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                hideKeyboard(search)
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText?.lowercase(Locale.getDefault()) ?: ""
                if (searchText.isNotEmpty()) {
                    topiclist.forEach {
                        if (it.title.lowercase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                } else {
                    searchList.addAll(topiclist)
                    search.clearFocus()
                    hideKeyboard(search)
                }
                recyclerView.adapter?.notifyDataSetChanged()

                return true
            }

        })

    }

    override fun onResume() {
        super.onResume()
        search.clearFocus()
        hideKeyboard(search)
    }

    private fun getData() {
        topiclist.clear()
        searchList.clear()
        for (x in imageList.indices) {
            val data = TopicData(TitleList[x], imageList[x])
            topiclist.add(data)
        }
        searchList.addAll(topiclist)
        recyclerView.adapter = TopicsAdapter(searchList)
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
