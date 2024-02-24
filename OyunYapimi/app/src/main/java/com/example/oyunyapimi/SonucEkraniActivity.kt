package com.example.oyunyapimi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.oyunyapimi.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {
    private lateinit var ulas : ActivitySonucEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        ulas = ActivitySonucEkraniBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(ulas.root)

        val skor = intent.getIntExtra("skor",0)
        ulas.textViewSonucSkor.text = skor.toString()

        val sp = getSharedPreferences("sonuc", Context.MODE_PRIVATE)
        val enYuksekSkor = sp.getInt("enYuksekSkor",0)

        if(skor > enYuksekSkor){
            val editor = sp.edit()
            editor.putInt("enYuksekSkor",skor)
            editor.commit()
            ulas.textViewSonucRekor.text = skor.toString()
        }else{
            ulas.textViewSonucRekor.text = enYuksekSkor.toString()
        }

        ulas.buttonTekrarDene.setOnClickListener {
            startActivity(Intent(this@SonucEkraniActivity,MainActivity::class.java))
        }

    }
}