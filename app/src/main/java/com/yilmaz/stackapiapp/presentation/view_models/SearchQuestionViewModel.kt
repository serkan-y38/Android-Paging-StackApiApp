package com.yilmaz.stackapiapp.presentation.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yilmaz.stackapiapp.data.paging.SearchQuestionPagingSource
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchQuestionViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    private val searchQuery = mutableStateOf("")

    private val _searchedQuestions = MutableStateFlow<PagingData<Question>>(PagingData.empty())
    val searchedQuestions = _searchedQuestions

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun searchQuestion(query: String) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                ),
                pagingSourceFactory = {
                    SearchQuestionPagingSource(
                        questionsRepository = questionsRepository,
                        query = query
                    )
                }
            ).flow.cachedIn(viewModelScope).collect {
                _searchedQuestions.value = it
            }
        }
    }
}