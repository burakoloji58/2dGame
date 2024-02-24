package com.example.oyunyapimi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.oyunyapimi.databinding.ActivityOyunEkraniBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivity : AppCompatActivity() {
    private lateinit var ulas : ActivityOyunEkraniBinding
    //pozisyonlar
    private var anaKarakterX = 0.0f
    private var anaKarakterY = 0.0f
    private var siyahKareX = 0.0f
    private var siyahKareY = 0.0f
    private var sariYuvarlakX = 0.0f
    private var sariYuvarlakY = 0.0f
    private var pembeUcgenX = 0.0f
    private var pembeUcgenY = 0.0f

    //boyutlar
    private var anaKarakterYuksekligi = 0
    private var anaKarakterGenisligi = 0
    private var ekranYuksekligi = 0
    private var ekranGenisligi = 0


    //kontroller
    private var dokunmaKontrol = false
    private var baslangicKontrol = false

    private var skor = 0

    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        ulas = ActivityOyunEkraniBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(ulas.root)

        //cisimleri dışarı çıkartma
        ulas.imageFilterViewKare.x = -8000.0f
        ulas.imageFilterViewKare.y = -8000.0f
        ulas.imageFilterViewUcgen.x = -800.0f
        ulas.imageFilterViewUcgen.y = -800.0f
        ulas.imageFilterViewYuvarlak.x = -800.0f
        ulas.imageFilterViewYuvarlak.y = -800.0f

        ulas.cl.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, event: MotionEvent?): Boolean {

                if(baslangicKontrol){
                    if (event?.action == MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent", "ACTION_DOWN : tıklandı")
                        dokunmaKontrol = true
                    }
                    if (event?.action == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent", "ACTION_UP : bırakıldı")
                        dokunmaKontrol = false
                    }
                }else{
                    baslangicKontrol= true

                    ulas.textViewOyunaBasla.visibility= View.INVISIBLE

                    anaKarakterX = ulas.imageFilterViewAnaKarakter.x
                    anaKarakterY = ulas.imageFilterViewAnaKarakter.y
                    anaKarakterGenisligi = ulas.imageFilterViewAnaKarakter.width
                    anaKarakterYuksekligi = ulas.imageFilterViewAnaKarakter.height
                    ekranGenisligi = ulas.cl.width
                    ekranYuksekligi = ulas.cl.height


                    timer.schedule(0,20){
                        Handler(Looper.getMainLooper()).post{
                            anaKarakterHareketi()
                            cisimlerinHareketi()
                            carpismaKontrolu()
                        }
                    }
                }
                return true
            }


        })

    }

    fun anaKarakterHareketi(){
        val anaKarakterHiz = ekranYuksekligi/60.0f
        if (dokunmaKontrol){
            anaKarakterY-=anaKarakterHiz
        }else{
            anaKarakterY+=anaKarakterHiz
        }

        if (anaKarakterY <= 0.0f){
            anaKarakterY = 0.0f
        }
        if (anaKarakterY >= ekranYuksekligi - anaKarakterYuksekligi){
            anaKarakterY = (ekranYuksekligi - anaKarakterYuksekligi).toFloat()
        }
        ulas.imageFilterViewAnaKarakter.y = anaKarakterY

    }

    fun cisimlerinHareketi(){
        siyahKareX-=ekranGenisligi/44.0f
        sariYuvarlakX-=ekranGenisligi/54.0f
        pembeUcgenX-=ekranGenisligi/36.0f

        if (siyahKareX < 0.0f){
            siyahKareX = ekranGenisligi +20.0f
            siyahKareY = floor(Math.random()*ekranYuksekligi).toFloat()
        }
        if (sariYuvarlakX < 0.0f){
            sariYuvarlakX = ekranGenisligi +20.0f
            sariYuvarlakY = floor(Math.random()*ekranYuksekligi).toFloat()
        }
        if (pembeUcgenX < 0.0f){
            pembeUcgenX = ekranGenisligi +20.0f
            pembeUcgenY = floor(Math.random()*ekranYuksekligi).toFloat()
        }

        ulas.imageFilterViewKare.x=siyahKareX
        ulas.imageFilterViewKare.y=siyahKareY
        ulas.imageFilterViewYuvarlak.x=sariYuvarlakX
        ulas.imageFilterViewYuvarlak.y=sariYuvarlakY
        ulas.imageFilterViewUcgen.x=pembeUcgenX
        ulas.imageFilterViewUcgen.y=pembeUcgenY
    }

    fun carpismaKontrolu(){
        val sariYuvarlakMerkezX = sariYuvarlakX+ulas.imageFilterViewYuvarlak.width/2
        val sariYuvarlakMerkezY = sariYuvarlakY+ulas.imageFilterViewYuvarlak.height/2

        if(0.0f <= sariYuvarlakMerkezY && sariYuvarlakMerkezX <= anaKarakterGenisligi
            && anaKarakterY <= sariYuvarlakMerkezY && sariYuvarlakY <=anaKarakterY + anaKarakterYuksekligi){

            skor += 20
            sariYuvarlakX =-10.0f
        }

        val pembeUcgenMerkezX = pembeUcgenX+ulas.imageFilterViewUcgen.width/2
        val pembeUcgenMerkezY = pembeUcgenY+ulas.imageFilterViewUcgen.height/2

        if(0.0f <= pembeUcgenMerkezY && pembeUcgenMerkezX <= anaKarakterGenisligi
            && anaKarakterY <= pembeUcgenMerkezY && pembeUcgenY <=anaKarakterY + anaKarakterYuksekligi){

            skor += 50
            pembeUcgenY =-10.0f
        }

        val siyahKareMerkezX = siyahKareX+ulas.imageFilterViewKare.width/2
        val siyahKareMerkezY = siyahKareY+ulas.imageFilterViewKare.height/2

        if(0.0f <= siyahKareMerkezY && siyahKareMerkezX <= anaKarakterGenisligi
            && anaKarakterY <= siyahKareMerkezY && siyahKareY <=anaKarakterY + anaKarakterYuksekligi){
            timer.cancel()
            siyahKareY =-10.0f

            val intent = Intent(this@OyunEkraniActivity,SonucEkraniActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
        ulas.textViewSkor.text = skor.toString()
    }
}