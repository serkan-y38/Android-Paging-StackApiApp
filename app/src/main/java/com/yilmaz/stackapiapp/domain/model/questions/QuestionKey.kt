package com.yilmaz.stackapiapp.domain.model.questions

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yilmaz.stackapiapp.utils.Constants.QUESTIONS_KEYS_TABLE_NAME
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
@Entity(tableName = QUESTIONS_KEYS_TABLE_NAME)
data class QuestionKey(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var prev: Int?,
    var next: Int?
)
