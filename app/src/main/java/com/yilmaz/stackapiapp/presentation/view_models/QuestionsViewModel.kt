package com.yilmaz.stackapiapp.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yilmaz.stackapiapp.data.local.dao.QuestionKeysDAO
import com.yilmaz.stackapiapp.data.local.dao.QuestionsDAO
import com.yilmaz.stackapiapp.data.local.db.AppDatabase
import com.yilmaz.stackapiapp.data.paging.QuestionRemoteMediator
import com.yilmaz.stackapiapp.domain.repository.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Suppress("CanBeParameter")
@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val questionsRepository: QuestionsRepository,
    private val appDatabase: AppDatabase,
    private val questionsDAO: QuestionsDAO,
    private val questionKeysDAO: QuestionKeysDAO
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(
            initialLoadSize = 20,
            enablePlaceholders = true,
            pageSize = 20,
            prefetchDistance = 10
        ),
        remoteMediator = QuestionRemoteMediator(
            questionsRepository = questionsRepository,
            appDatabase = appDatabase,
            questionsDAO = questionsDAO,
            questionKeysDAO = questionKeysDAO
        )
    ) {
        questionsDAO.getAllQuestions()
    }.flow.cachedIn(viewModelScope)

}