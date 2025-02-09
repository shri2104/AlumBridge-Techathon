package com.example.readerapp.Retrofit

import androidx.compose.ui.text.input.TextFieldValue
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class EventData(
    val Headline: String?,
    val Description: String?,
    val Dates: String?,
    val Location: String?,
    val Forms: String?,
    val EventType: String?
)

data class ProfileData(
    val Name: String?,
    val Email: String?,
    val Phonenumber: String?,
    val Location: String?,
    val Batch: String?,
    val CurrentworkStatus: String?
)

data class DonationData(
    val AccountHolderName: String?,
    val BankName: String?,
    val AccountNumber: String?,
    val IFSCcode: String?,
)

data class ApiResponse(val success: Boolean, val id: String?)

interface ApiService {
    @POST("storeEventData")
    suspend fun StoreEventData(@Body jobData: EventData): Response<ApiResponse>
    @GET("getEvents")
    suspend fun getAllEvents(): List<EventData>
    @POST("StoreDonationData01")
    suspend fun StoreDonationdata(@Body jobData: DonationData): Response<ApiResponse>

}

fun createApiservice(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}