package com.example.myapplication3.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication3.R
import com.example.myapplication3.databinding.ActivityDetailBinding
import com.example.myapplication3.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val product = intent.getParcelableExtra<Product>("product")
        product?.let {
            setupUI(it)
            viewModel.addRecentlyViewed(it)
        }
    }

    private fun setupUI(product: Product) {
        binding.tvName.text = product.name
        binding.tvPrice.text = product.price
        Glide.with(this)
            .load(product.imageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(binding.ivProduct)

        binding.btnOpenInBrowser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(product.productLink))
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}