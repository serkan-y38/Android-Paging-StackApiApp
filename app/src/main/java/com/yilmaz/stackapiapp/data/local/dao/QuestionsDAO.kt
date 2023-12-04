package com.yilmaz.stackapiapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.yilmaz.stackapiapp.domain.model.questions.Question

@Dao
interface QuestionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuestions(list: List<Question>)

    @Query("SELECT * FROM questions_table")
    fun getAllQuestions(): PagingSource<Int, Question>

    @Query("DELETE FROM questions_table")
    suspend fun deleteAllQuestions()

}