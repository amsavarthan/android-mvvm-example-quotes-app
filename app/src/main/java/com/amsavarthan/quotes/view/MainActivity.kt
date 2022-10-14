package com.amsavarthan.quotes.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.amsavarthan.quotes.R
import com.amsavarthan.quotes.data.api.Resource
import com.amsavarthan.quotes.databinding.ActivityMainBinding
import com.amsavarthan.quotes.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getRandomQuote()
        viewModel.quote.observe(this) { response ->
            when (response) {
                is Resource.Error -> {
                    binding.progressHorizontal.isVisible = false
                    Snackbar.make(
                        binding.root,
                        response.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.progressHorizontal.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressHorizontal.isVisible = false
                    response.data!!.let { quote ->
                        binding.quote.text = quote.content
                        binding.author.text = quote.author.padStart(1, '-')
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.activity_main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                viewModel.getRandomQuote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}