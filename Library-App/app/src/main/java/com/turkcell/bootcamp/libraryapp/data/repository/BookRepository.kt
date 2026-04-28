package com.turkcell.bootcamp.libraryapp.data.repository

import com.turkcell.bootcamp.libraryapp.data.model.Book
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
        supabase.postgrest["books"].insert(book)
    }

    // Ã–DEV 2: BookRepository GÃ¼ncelleme, silme, arama fonksiyonlarÄ±nÄ± tanÄ±mla.
}
