package com.turkcell.bootcamp.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.turkcell.bootcamp.libraryapp.data.model.Book
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.BookViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    onNavigateToBorrowRecords: () -> Unit
) {
    val profileState by authViewModel.profile.collectAsState()
    val books by bookViewModel.books.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    val borrowMessage by bookViewModel.borrowMessage.collectAsState()
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var borrowDays by remember { mutableStateOf("5") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kitaplar",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Button(onClick = onNavigateToBorrowRecords) {
                Text("Kiralamalarim")
            }
        }

        borrowMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterHorizontally),
                strokeWidth = 2.dp
            )

            books.isEmpty() -> Text("Kitaplar yuklenemedi.")

            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    BookCard(
                        book = book,
                        onBorrowClick = {
                            borrowDays = "5"
                            selectedBook = book
                            bookViewModel.clearBorrowMessage()
                        }
                    )
                }
            }
        }
    }

    selectedBook?.let { book ->
        AlertDialog(
            onDismissRequest = { selectedBook = null },
            title = { Text("Odunc alma suresi") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Kitabi en fazla 5 gun odunc alabilirsiniz.")
                    OutlinedTextField(
                        value = borrowDays,
                        onValueChange = { borrowDays = it },
                        label = { Text("Gun sayisi") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val studentId = profileState?.userId
                        val dayCount = borrowDays.toIntOrNull()

                        if (studentId != null && dayCount != null) {
                            bookViewModel.borrowBook(book, studentId, dayCount.coerceIn(1, 5))
                            selectedBook = null
                        }
                    }
                ) {
                    Text("ODUNC AL")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedBook = null }) {
                    Text("Vazgec")
                }
            }
        )
    }
}

@Composable
fun BookCard(
    book: Book,
    onBorrowClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = book.category,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "${book.avaiableCopies}/${book.totalCopies} mevcut",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = "ISBN: ${book.isbn} - ${book.pageCount} sayfa",
                style = MaterialTheme.typography.bodySmall
            )

            if (book.avaiableCopies > 0) {
                Button(
                    onClick = onBorrowClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ODUNC AL")
                }
            } else {
                Text(
                    text = "STOKTA YOK",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
