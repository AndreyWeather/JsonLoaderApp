package com.example.jsonloader


import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter





class Loader {



    fun jsonLoader(): String {

        val url = URL("https://run.mocky.io/v3/956d7c43-b513-4698-aa7d-6a9bfd4f1bec")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()



        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val json = bufferedReader.use(BufferedReader::readText)
            bufferedReader.close()
            return json
        } else {
            return ("Ошибка получения JSON: $responseCode")
        }

    }

    fun jsonParsing(fileInJsonFormat: String): MutableList<User> {
        val UserList = mutableListOf<User>()
        val jsonObjectUsers = JSONTokener(fileInJsonFormat).nextValue() as JSONObject
        val jsonArray = jsonObjectUsers.getJSONArray("result")

        for (i in 0 until jsonArray.length()) {
            val jsonObjectUser = jsonArray[i].toString()
            val userJson = JSONTokener(jsonObjectUser).nextValue() as JSONObject

            val _id = userJson.getInt("id")
            val _email = userJson.getString("email")
            val email = _email.substring(8)
            val firstName = userJson.getString("firstName")
            val lastName = userJson.getString("lastName")
            val _dateUpdate = userJson.getInt("dateUpdate")

            @RequiresApi(Build.VERSION_CODES.O)
            fun dataFormatter(data: Int = _dateUpdate): String {
                val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val time = _dateUpdate
                val timeToLong = time.toLong()
                val instant = Instant.ofEpochMilli(timeToLong)
                val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                val dataInUTF = formatter.format(date)
                return dataInUTF
            }

            val dateUpdate = dataFormatter()
            val id = _id.toString()

            val User = User(id, email, firstName, lastName, dateUpdate)

            UserList.add(User)
        }
        return UserList
    }
}


