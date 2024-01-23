package com.yilmaz.stackapiapp.domain.model.questions

import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yilmaz.stackapiapp.utils.Constants.QUESTIONS_TABLE_NAME
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Immutable
@Stable
@Parcelize
@Entity(tableName = QUESTIONS_TABLE_NAME)
data class Question(
    @PrimaryKey(autoGenerate = false)
    val questionId: String,
    @ColumnInfo("link")
    val link: String,
    @Embedded
    val owner: Owner,
    @ColumnInfo("tags")
    val tags: List<String>,
    @ColumnInfo("title")
    val title: String,
) : Parcelable
