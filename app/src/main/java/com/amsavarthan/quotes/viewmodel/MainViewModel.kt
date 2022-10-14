package com.amsavarthan.quotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amsavarthan.quotes.data.api.Resource
import com.amsavarthan.quotes.data.model.Quote
import com.amsavarthan.quotes.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
) : ViewModel() {

    private val _quote = MutableLiveData<Resource<Quote>>(Resource.Loading())
    val quote: LiveData<Resource<Quote>> get() = _quote

    fun getRandomQuote() {
        viewModelScope.launch {
            quotesRepository.getRandomQuote().collect {
                _quote.value = it
            }
        }
    }

}