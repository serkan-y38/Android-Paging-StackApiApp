package com.yilmaz.stackapiapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import javax.inject.Inject

class SearchQuestionPagingSource @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val query: String
) : PagingSource<Int, Question>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {
        val currentPage = params.key ?: 1
        return try {
            val response =
                questionsRepository.searchQuestions(
                    page = currentPage,
                    limit = 20,
                    query = query
                )
            if (response.data!!.isNotEmpty()) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Question>): Int? {
        return state.anchorPosition
    }

}