package com.turkcell.bootcamp.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.bootcamp.libraryapp.data.model.Book
import com.turkcell.bootcamp.libraryapp.data.model.BorrowRecord
import com.turkcell.bootcamp.libraryapp.data.model.BorrowRecordInsert
import com.turkcell.bootcamp.libraryapp.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookViewModel : ViewModel() {
    private val repository = BookRepository()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _borrowRecords = MutableStateFlow<List<BorrowRecord>>(emptyList())
    val borrowRecords: StateFlow<List<BorrowRecord>> = _borrowRecords

    private val _borrowMessage = MutableStateFlow<String?>(null)
    val borrowMessage: StateFlow<String?> = _borrowMessage

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            repository
                .getAllBooks()
                .onSuccess { _books.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun borrowBook(book: Book, studentId: String, dayCount: Int) {
        viewModelScope.launch {
            if (book.avaiableCopies <= 0) {
                _borrowMessage.value = "Kitap stokta yok."
                return@launch
            }

            if (dayCount !in 1..5) {
                _borrowMessage.value = "En fazla 5 gun odunc alabilirsiniz."
                return@launch
            }

            _isLoading.value = true
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val borrowedAt = Calendar.getInstance()
            val dueDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, dayCount)
            }

            val record = BorrowRecordInsert(
                studentId = studentId,
                bookId = book.id,
                borrowedAt = dateFormat.format(borrowedAt.time),
                dueDate = dateFormat.format(dueDate.time)
            )

            repository
                .borrowBook(record)
                .onSuccess {
                    _borrowMessage.value = "Kitap odunc alindi."
                    loadBooks()
                    loadBorrowRecords(studentId)
                }
                .onFailure { _borrowMessage.value = it.message }

            _isLoading.value = false
        }
    }

    fun loadBorrowRecords(studentId: String) {
        viewModelScope.launch {
            repository
                .getBorrowRecords(studentId)
                .onSuccess { _borrowRecords.value = it }
                .onFailure { _error.value = it.message }
        }
    }

    fun clearBorrowMessage() {
        _borrowMessage.value = null
    }
}
