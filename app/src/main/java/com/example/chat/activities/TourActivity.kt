package com.example.chat.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.example.chat.databinding.ActivityTourBinding

class TourActivity:AppCompatActivity(){
    private lateinit var binding:ActivityTourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tourImage.alpha=0f

        binding.tourImage.animate().setDuration(1600).alpha(1f).withEndAction{
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out)
            finish()
        }
        binding.tourText.animate().setDuration(1600).alpha(1f).withEndAction{
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(com.google.android.material.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out)
            finish()
        }

    }

}