package com.yilmaz.stackapiapp.domain.repository

import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.utils.Resource

interface QuestionsRepository {

    suspend fun getQuestions(page: Int, limit: Int): Resource<List<Question>>

    suspend fun searchQuestions(page: Int, limit: Int, query: String): Resource<List<Question>>

}