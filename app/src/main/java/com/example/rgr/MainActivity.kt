package com.example.rgr

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.rgr.databinding.ActivityMainBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getRandomImageButton.setOnClickListener {
            onGetRandomImage() }

        binding.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            return@setOnEditorActionListener onGetRandomImage() //натискання на пошук на клавіатурі
        }
    }


    private fun onGetRandomImage():Boolean {
        var key = binding.editText.text.toString()
        if(key.isBlank()){
            binding.editText.error="No associations"
            return true
        }

        val multi = MultiTransformation<Bitmap>(
            BlurTransformation(binding.seekBarBlur.progress.toInt())//наскільки сильно заблюрювати
        )

        var enkodedAssociatingWord = URLEncoder.encode(key, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600?$enkodedAssociatingWord")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)//щоб не видавалася постійно 1 й та сама фотка
            .apply (RequestOptions.bitmapTransform(multi))
            .into(binding.testImage)
        return false
    }

}