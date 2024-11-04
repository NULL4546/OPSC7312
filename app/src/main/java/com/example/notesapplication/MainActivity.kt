package com.example.notesapplication

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved language preference before setting the content
        val languageCode = getLanguagePreference(this)
        setLocale(this, languageCode)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set up the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set the ActionBar title text color to white
        toolbar.setTitleTextColor(Color.WHITE)

        // Apply window insets for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Link the ActionBar with the NavController for fragment navigation
        setupActionBarWithNavController(navController)

        // Update ActionBar title based on the current destination using localized strings
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.noteFragment -> supportActionBar?.title = getString(R.string.first_fragment_label)
                R.id.addEditNoteFragment -> supportActionBar?.title = getString(R.string.second_fragment_label)
                else -> supportActionBar?.title = destination.label
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_language -> {
                // Handle the language switch
                toggleLanguage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Function to toggle the language
    private fun toggleLanguage() {
        val currentLanguage = getLanguagePreference(this)
        val newLanguage = if (currentLanguage == "en") "af" else "en" // Toggle between English and Afrikaans

        // Update the locale and save the preference
        setLocale(this, newLanguage)
        saveLanguagePreference(this, newLanguage)

        // Restart the activity to apply the language change
        recreate()
    }

    // Function to set the locale
    private fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    // Function to save the language preference
    private fun saveLanguagePreference(context: Context, languageCode: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("App_Language", languageCode)
        editor.apply()
    }

    // Function to get the saved language preference
    private fun getLanguagePreference(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return sharedPreferences.getString("App_Language", "en") ?: "en" // Default to English
    }

    // Handle back navigation with NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
