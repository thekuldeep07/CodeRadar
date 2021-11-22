package com.example.CodeRadar.data.api

import com.example.coderadar.data.api.ClistAPIService
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime


class ClistApiServiceTest {
    private lateinit var service: ClistAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp(){
        val gson = GsonBuilder()
            .setLenient()
            .create()
        server= MockWebServer()
        service=Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ClistAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getContestPresent_sentRequest_recievedExpected(){
        runBlocking {
            enqueueMockResponse("contestsResponses.json")
            val localDateTime = LocalDateTime.parse("2021-11-22T00:00:59")

//            Log.d("date",""+localDateTime)
//            val localDateTime1:LocalDateTime="2019-08-07T00:00"
//            Log.d("localDateTime")
            val responseBody=service.getContestPresent(
                start_dt = localDateTime,
            ).body()
            val request=server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/v2/json/contest/?username=thekuldeep07&api_key=f9be2617619f7d5e2378cde16be75208ad81d36a&start__gt=2021-11-22T00%3A00%3A59&order_by=start")

        }

    }

    @Test
    fun getContestPresent_recievedResponse(){
        runBlocking {
            enqueueMockResponse("contestsResponses.json")
            val localDateTime = LocalDateTime.parse("2021-11-07T00:00:59")


//            Log.d("date",""+localDateTime)
//            val localDateTime1:LocalDateTime="2019-08-07T00:00"
//            Log.d("localDateTime")
            val responseBody=service.getContestPresent(
                    start_dt = localDateTime,
            ).body()
            val contestList = responseBody!!.contests
            assertThat(contestList.size).isEqualTo(2)
        }
    }

    @Test
    fun correctContent_recievedResponse(){
        runBlocking {
            enqueueMockResponse("contestsResponses.json")
            val localDateTime = LocalDateTime.parse("2021-11-07T00:00:59")


//            Log.d("date",""+localDateTime)
//            val localDateTime1:LocalDateTime="2019-08-07T00:00"
//            Log.d("localDateTime")
            val responseBody=service.getContestPresent(
                    start_dt = localDateTime,
            ).body()
            val contestList = responseBody!!.contests
            val contest = contestList[0]
            assertThat(contest.event).isEqualTo("I3 – Innovate – Ideate – Implement. Prototype Submission Phase. Online")
        }
    }



    @After
    fun tearDown(){
        server.shutdown()
    }
}