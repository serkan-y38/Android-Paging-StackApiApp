package com.yilmaz.stackapiapp.data.remote.repository

import com.yilmaz.stackapiapp.data.remote.api_service.StackApiService
import com.yilmaz.stackapiapp.data.remote.mappers.toDomain
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import com.yilmaz.stackapiapp.utils.Resource
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    private val apiService: StackApiService
) : QuestionsRepository {

    override suspend fun getQuestions(page: Int, limit: Int): Resource<List<Question>> {
        return try {
            val response = apiService.getQuestions(page = page, pagesize = limit)
            if (response.isSuccessful)
                Resource.Success(response.body()?.items?.toDomain())
            else
                Resource.Error(response.errorBody()?.string())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun searchQuestions(
        page: Int,
        limit: Int,
        query: String
    ): Resource<List<Question>> {
        return try {
            val response = apiService.searchQuestion(page = page, pagesize = limit, query = query)
            if (response.isSuccessful)
                Resource.Success(response.body()?.items?.toDomain())
            else
                Resource.Error(response.errorBody()?.string())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }
}