package com.yilmaz.stackapiapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yilmaz.stackapiapp.domain.model.questions.QuestionKey

@Dao
interface QuestionKeysDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuestionKeys(l: List<QuestionKey>)

    @Query("DELETE FROM question_keys_table")
    suspend fun deleteAllQuestionKeys()

    @Query("SELECT * FROM question_keys_table WHERE id=:id")
    suspend fun getKey(id: String): QuestionKey

}