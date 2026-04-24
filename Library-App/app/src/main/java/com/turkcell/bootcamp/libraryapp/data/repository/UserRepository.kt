package com.turkcell.bootcamp.libraryapp.data.repository

import com.turkcell.bootcamp.libraryapp.data.model.User

class UserRepository {
    private val users = mutableListOf<User>()

    fun register(user: User): Boolean {
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            return false
        }
        users.add(user)
        return true
    }

    fun authenticate(email: String, password: String): User? {
        return users.firstOrNull { it.email.equals(email, ignoreCase = true) && it.password == password }
    }
}
