package com.amsavarthan.quotes.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.amsavarthan.quotes.R
import com.amsavarthan.quotes.databinding.ActivityMainBinding
import com.amsavarthan.quotes.viewmodel.MainViewModel
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

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.quote.flowWithLifecycle(lifecycle).collectLatest { quote ->
                        if (quote == null) viewModel.getRandomQuote()
                        binding.quote.text = quote?.content
                        binding.author.text = quote?.author?.padStart(1, '-')
                    }
                }
                launch {
                    viewModel.isLoading.flowWithLifecycle(lifecycle).collectLatest { isLoading ->
                        binding.progressHorizontal.isVisible = isLoading
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