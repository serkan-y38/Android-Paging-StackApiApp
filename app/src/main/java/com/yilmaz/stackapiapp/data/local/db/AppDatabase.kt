package com.yilmaz.stackapiapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yilmaz.stackapiapp.data.local.dao.QuestionKeysDAO
import com.yilmaz.stackapiapp.data.local.dao.QuestionsDAO
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.domain.model.questions.QuestionKey
import com.yilmaz.stackapiapp.data.local.db.TypeConverters as Converters

@Database(entities = [Question::class, QuestionKey::class], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionsDAO(): QuestionsDAO
    abstract fun questionKeysDAO(): QuestionKeysDAO
}