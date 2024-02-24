package com.example.oyunyapimi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyunyapimi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var ulas : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        ulas = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(ulas.root)

        ulas.buttonBasla.setOnClickListener {
            startActivity(Intent(this@MainActivity,OyunEkraniActivity::class.java))
        }

    }
}