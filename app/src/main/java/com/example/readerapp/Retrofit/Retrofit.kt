package com.example.readerapp.Retrofit

import com.example.readerapp.jobData.JobPosting
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class EventData(
    val userId: String?,
    val Headline: String?,
    val Description: String?,
    val Dates: String?,
    val Location: String?,
    val Forms: String?,
    val EventType: String?,
)


data class ProfileData(
    val Name: String,
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

data class Donatedinfo(
    val Amount: String?,
    val Donarname: String?,
    val Batch: String?,
)

data class InstituteData(
    val userId: String,  // Add userId field
    val name: String,
    val type: String,
    val location: String,
    val establishmentYear: String,
    val address: String,
    val affiliation: String,
    val contactEmail: String,
    val contactNumber: String,
    val websiteURL: String
)

data class JobPosting(
    val title: String,
    val company: String,
    val role: String,
    val description: String,
    val applyLink: String
)

data class emailuserid(
   val email:String,
    val userId: String?
)

data class ApiResponse(val success: Boolean, val id: String?)

interface ApiService {

    @POST("StoreInstitutedata")
    suspend fun Institutedata(@Body instituteData: InstituteData): Response<ApiResponse>

    @GET("getInstituteData/{userId}")
    suspend fun Getinstitutedata(@Path("userId") userId: String): InstituteData

    @POST("storeuserid")
    suspend fun Storeuserid(@Body emailuserid: emailuserid): Response<ApiResponse>

    @GET("getuserid/{email}")
    suspend fun getuserid(@Path("email") email: String): emailuserid

    @POST("storeEventData")
    suspend fun StoreEventData(@Body jobData: EventData): Response<ApiResponse>

    @GET("getEvents")
    suspend fun getAllEvents(): List<EventData>

    @POST("StoreDonationData01")
    suspend fun StoreDonationdata(@Body jobData: DonationData): Response<ApiResponse>
    @POST("Storeprofiledata")
    suspend fun StoreProfiledata(@Body jobData: ProfileData): Response<ApiResponse>
    @POST("storeDonationInfo")
    suspend fun StoreDonationInfo(@Body jobData: Donatedinfo): Response<ApiResponse>
    @POST("StoreJob")
    suspend fun postJob(@Body jobPosting: JobPosting): Response<ApiResponse>
}

fun createApiservice(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}