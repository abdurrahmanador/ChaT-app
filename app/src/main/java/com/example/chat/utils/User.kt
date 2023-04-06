package com.example.chat.utils

data class User (
    val name: String? = null,
    val uid: String? = null
) {
    constructor() : this(null, null)
}
