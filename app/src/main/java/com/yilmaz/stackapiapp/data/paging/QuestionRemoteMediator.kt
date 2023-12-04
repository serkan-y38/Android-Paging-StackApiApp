package com.yilmaz.stackapiapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yilmaz.stackapiapp.data.local.dao.QuestionKeysDAO
import com.yilmaz.stackapiapp.data.local.dao.QuestionsDAO
import com.yilmaz.stackapiapp.data.local.db.AppDatabase
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.domain.model.questions.QuestionKey
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import com.yilmaz.stackapiapp.utils.Resource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class QuestionRemoteMediator @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val appDatabase: AppDatabase,
    private val questionsDAO: QuestionsDAO,
    private val questionKeysDAO: QuestionKeysDAO
) : RemoteMediator<Int, Question>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Question>
    ): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getKeyClosestToCurrentPosition(state)
                    remoteKeys?.next?.minus(1) ?: 1
                }

                LoadType.APPEND -> {
                    val remoteKeys = getLastKey(state)
                    remoteKeys?.next
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getClosetKey(state)
                    remoteKeys?.prev
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }
            val response = questionsRepository.getQuestions(
                page = page,
                limit = state.config.pageSize
            )
            val endOfPagination = response.data?.size!! < state.config.pageSize

            when (response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        appDatabase.withTransaction {
                            questionKeysDAO.deleteAllQuestionKeys()
                            questionsDAO.deleteAllQuestions()
                        }
                    }
                    val prev = if (page == 1) 1 else page - 1
                    val next = if (endOfPagination) null else page + 1

                    val questions = response.data

                    val keys = questions.map {
                        QuestionKey(id = it.questionId, prev, next)
                    }
                    appDatabase.withTransaction {
                        keys.let { questionKeysDAO.insertAllQuestionKeys(keys) }
                        questions.let { questionsDAO.insertAllQuestions(questions) }
                    }
                }

                is Resource.Error -> {
                    MediatorResult.Error(Exception())
                }

                is Resource.Loading -> {
                    Log.i("questions mediator resource ->", "Loading")
                }
            }
            if (response is Resource.Success) {
                if (endOfPagination)
                    MediatorResult.Success(true)
                else
                    MediatorResult.Success(false)
            } else {
                MediatorResult.Success(true)
            }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, Question>
    ): QuestionKey? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?.questionId?.let { id ->
            questionKeysDAO.getKey(id = id)
        }
    }

    private suspend fun getLastKey(
        state: PagingState<Int, Question>
    ): QuestionKey? = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
        ?.let { question ->
            questionKeysDAO.getKey(id = question.questionId)
        }

    private suspend fun getClosetKey(
        state: PagingState<Int, Question>
    ): QuestionKey? = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
        ?.let { question ->
            questionKeysDAO.getKey(id = question.questionId)
        }

}