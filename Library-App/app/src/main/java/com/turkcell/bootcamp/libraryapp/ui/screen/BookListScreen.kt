package com.turkcell.bootcamp.libraryapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.turkcell.bootcamp.libraryapp.data.model.Book

private val sampleBooks = listOf(
    Book("Kendine Ait Bir Oda", "Virginia Woolf", "Klasik drama ve toplumsal analiz"),
    Book("Sefiller", "Victor Hugo", "Fransız devrimi sonrası dramatik hikaye"),
    Book("Bülbülü Öldürmek", "Harper Lee", "Adalet ve insan doğası üzerine"),
    Book("Başlangıç", "Dan Brown", "Gerilimli bir sanat tarihi hikayesi")
)

@Composable
fun BookListScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(sampleBooks) { book ->
                BookCard(book)
            }
        }
    }
}

@Composable
private fun BookCard(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Yazar: ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = book.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
