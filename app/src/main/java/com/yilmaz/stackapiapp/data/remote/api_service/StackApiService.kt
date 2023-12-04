package com.yilmaz.stackapiapp.data.remote.api_service

import com.yilmaz.stackapiapp.data.remote.dto.questions_dto.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StackApiService {

    @GET("questions")
    suspend fun getQuestions(
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "activity",
        @Query("site") site: String = "stackoverflow",
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
    ): Response<ResponseDTO>

    @GET("search")
    suspend fun searchQuestion(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "activity",
        @Query("intitle") query: String,
        @Query("site") site: String = "stackoverflow"
    ): Response<ResponseDTO>

}