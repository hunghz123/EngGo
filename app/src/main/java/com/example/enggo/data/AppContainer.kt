package com.example.enggo.data

import android.content.Context
import androidx.room.Room
import com.example.enggo.R
import com.example.enggo.data.dictionary.DictionaryBaseRepository
import com.example.enggo.data.dictionary.DictionaryDao
import com.example.enggo.data.dictionary.DictionaryDatabase
import com.example.enggo.data.dictionary.DictionaryRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

interface AppContainer {

    val dictionaryRepository: DictionaryBaseRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    //private val baseUrl = "http://localhost:5000/"
    private val baseUrl = "http://10.0.2.2:5000/"
    //private val baseUrl = "http://127.0.0.1:5000/"


    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()


    /**
     * DI implementation for dictionary
     */
    private val dictionaryDao: DictionaryDao by lazy {
        dictionaryDatabase.dictionaryDao
    }

    override val dictionaryRepository: DictionaryBaseRepository by lazy {
        DictionaryRepository(dictionaryDao)
    }

    private val dictionaryDatabase: DictionaryDatabase by lazy {
        Room.databaseBuilder(context, DictionaryDatabase::class.java, "dictionaryDb")
            .createFromAsset("wordDB")
            .fallbackToDestructiveMigration()
            .build()
    }
}