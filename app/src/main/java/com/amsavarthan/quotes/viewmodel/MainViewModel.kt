package com.amsavarthan.quotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsavarthan.quotes.data.model.Quote
import com.amsavarthan.quotes.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote = _quote.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getRandomQuote() {
        viewModelScope.launch {
            _isLoading.emit(true)
            _quote.emit(quotesRepository.getRandomQuote().body())
            _isLoading.emit(false)
        }
    }

}