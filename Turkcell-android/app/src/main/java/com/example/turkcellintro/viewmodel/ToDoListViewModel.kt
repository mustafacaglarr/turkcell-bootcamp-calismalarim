package com.example.turkcellintro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.turkcellintro.data.TodoRepository

class ToDoListViewModel : ViewModel()
{
   private  val repository = TodoRepository()

}