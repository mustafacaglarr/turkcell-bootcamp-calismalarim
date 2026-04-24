package com.example.turkcellintro.data

import com.example.turkcellintro.model.Todo
import retrofit2.http.GET

interface TodoApiService {

    @GET
    suspend fun getTodos(): List<Todo>
}