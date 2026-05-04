package com.turkcell.bootcamp.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turkcell.bootcamp.libraryapp.data.model.BorrowRecord
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.BookViewModel

@Composable
fun BorrowRecordsScreen(
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    onBack: () -> Unit
) {
    val profile by authViewModel.profile.collectAsState()
    val borrowRecords by bookViewModel.borrowRecords.collectAsState()
    val studentId = profile?.userId

    LaunchedEffect(studentId) {
        if (studentId != null) {
            bookViewModel.loadBorrowRecords(studentId)
        }
    }

    val activeRecords = borrowRecords.filter { it.returnedAt == null }
    val previousRecords = borrowRecords.filter { it.returnedAt != null }

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
                text = "Kiralamalarim",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Button(onClick = onBack) {
                Text("Geri")
            }
        }

        if (studentId == null) {
            Text("Kiralama bilgileri icin giris yapmalisiniz.")
            return@Column
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Aktif kiralamalar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (activeRecords.isEmpty()) {
                item { Text("Aktif kiralamaniz yok.") }
            } else {
                items(activeRecords, key = { it.id }) { record ->
                    BorrowRecordCard(record = record, status = "Aktif")
                }
            }

            item {
                Text(
                    text = "Onceki kiralamalar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (previousRecords.isEmpty()) {
                item { Text("Onceki kiralamaniz yok.") }
            } else {
                items(previousRecords, key = { it.id }) { record ->
                    BorrowRecordCard(record = record, status = "Tamamlandi")
                }
            }
        }
    }
}

@Composable
fun BorrowRecordCard(
    record: BorrowRecord,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Kitap ID: ${record.bookId}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text("Alis tarihi: ${record.borrowedAt}")
            Text("Teslim tarihi: ${record.dueDate}")
            Text("Durum: $status")
        }
    }
}
