package com.quanttmex.quantumcomputingbasics

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import android.text.Html

class ReadingActivity : AppCompatActivity() {

    private lateinit var articleText: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var btnPrevious: Button
    private lateinit var btnDarkMode: Button
    private lateinit var btnNextTopic: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var currentIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        // Initialize UI components
        articleText = findViewById(R.id.article_text)
        scrollView = findViewById(R.id.scroll_view)
        btnPrevious = findViewById(R.id.btn_brightness)
        btnDarkMode = findViewById(R.id.btn_dark_mode)
        btnNextTopic = findViewById(R.id.btn_next_topic)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        currentIndex = intent.getIntExtra("INDEX", -1)

        // Load content based on index
        loadContent(currentIndex)

        // Set up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Load saved settings
        loadSettings()

        btnPrevious.setOnClickListener {
            loadPreviousTopic()
        }

        btnDarkMode.setOnClickListener {
            toggleDarkMode()
        }

        btnNextTopic.setOnClickListener {
            loadNextTopic()
        }
    }

    private fun loadSettings() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        applyTheme(isDarkMode)
    }

    private fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            setDarkMode()
        } else {
            setLightMode()
        }
    }

    private fun toggleDarkMode() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        with(sharedPreferences.edit()) {
            putBoolean("dark_mode", !isDarkMode)
            apply()
        }
        applyTheme(!isDarkMode)
    }

    private fun setDarkMode() {
        articleText.setTextColor(ContextCompat.getColor(this, R.color.white))
        scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun setLightMode() {
        articleText.setTextColor(ContextCompat.getColor(this, R.color.black))
        scrollView.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun loadNextTopic() {
        if (currentIndex < 9) { // Assuming there are 10 topics
            currentIndex++
            loadContent(currentIndex)
        }
    }

    private fun loadPreviousTopic() {
        if (currentIndex > 0) {
            currentIndex--
            loadContent(currentIndex)
        }
    }

    private fun loadContent(index: Int) {
        val content = when (index) {
            0 -> getString(R.string.topic1_content)
            1 -> getString(R.string.topic2_content)
            2 -> getString(R.string.topic3_content)
            3 -> getString(R.string.topic4_content)
            4 -> getString(R.string.topic5_content)
            5 -> getString(R.string.topic6_content)
            6 -> getString(R.string.topic7_content)
            7 -> getString(R.string.topic8_content)
            8 -> getString(R.string.topic9_content)
            9 -> getString(R.string.topic10_content)
            else -> "Content not found"
        }
        articleText.text = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)
    }
}
