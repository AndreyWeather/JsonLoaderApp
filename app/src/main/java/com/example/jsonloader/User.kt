package com.example.jsonloader

@Suppress("UNREACHABLE_CODE")
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateUpdate: String
) {

    override fun toString(): String = text


    val text = """
    id ${id.toString()}
    email $email
    firstName $firstName
    lastName $lastName
    dateUpdate $dateUpdate
    """

}
