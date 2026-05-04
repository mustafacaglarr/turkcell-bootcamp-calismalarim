package com.turkcell.bootcamp.libraryapp.data.repository

import com.turkcell.bootcamp.libraryapp.data.model.Book
import com.turkcell.bootcamp.libraryapp.data.model.BorrowRecord
import com.turkcell.bootcamp.libraryapp.data.model.BorrowRecordInsert
import com.turkcell.bootcamp.libraryapp.data.supabase.*
import io.github.jan.supabase.postgrest.postgrest

class BookRepository {
    suspend fun getAllBooks(): Result<List<Book>> = runCatching {
        supabase.postgrest["books"]
            .select()
            .decodeList<Book>()
    }

    suspend fun getBookById(id:String): Result<Book> = runCatching {
        supabase.postgrest["books"]
            .select { filter { eq("id",id) } }
            .decodeSingle<Book>()
    }

    suspend fun addBook(book: Book): Result<Unit> = runCatching {
        if(book.title.length < 3)
            return@runCatching
        supabase.postgrest["books"].insert(book)
    }

    suspend fun updateBook(book: Book): Result<Unit> = runCatching {
        supabase.postgrest["books"]
            .update(book) {
                filter { eq("id", book.id) }
            }
    }

    suspend fun deleteBook(id: String): Result<Unit> = runCatching {
        supabase.postgrest["books"]
            .delete {
                filter { eq("id", id) }
            }
    }

    suspend fun searchBooks(query: String): Result<List<Book>> = runCatching {
        val searchText = "%${query.trim()}%"

        supabase.postgrest["books"]
            .select {
                filter {
                    or {
                        ilike("title", searchText)
                        ilike("author", searchText)
                        ilike("isbn", searchText)
                        ilike("category", searchText)
                    }
                }
            }
            .decodeList<Book>()
    }

    suspend fun borrowBook(record: BorrowRecordInsert): Result<Unit> = runCatching {
        val book = getBookById(record.bookId).getOrThrow()
        if (book.avaiableCopies <= 0) error("Kitap stokta yok.")

        supabase.postgrest["borrow_records"].insert(record)

        updateBook(
            book.copy(avaiableCopies = book.avaiableCopies - 1)
        ).getOrThrow()
    }

    suspend fun getBorrowRecords(studentId: String): Result<List<BorrowRecord>> = runCatching {
        supabase.postgrest["borrow_records"]
            .select {
                filter { eq("student_id", studentId) }
            }
            .decodeList<BorrowRecord>()
    }
}
//  ÖDEV:
// - Kitapların kiralanma işi -> Kartta availableCopies>0 ise ÖDÜNÇ AL butonu <=0 ise STOKTA YOK göstergesi
// - BorrowRecord tablosunu ve policylerini oluştur..
// - ÖDÜNÇ AL butonu ilgili bilgileri isteyip-hesaplayarak (maks 5 gün) BorrowRecord tablosuna atalım.
// - Kiralamalar sayfası -> Giriş yapmış öğrencinin önceki-aktif kiralamalarını listeleyen bir sayfa.
