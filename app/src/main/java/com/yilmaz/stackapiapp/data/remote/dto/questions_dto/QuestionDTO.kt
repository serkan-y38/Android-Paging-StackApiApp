package com.yilmaz.stackapiapp.data.remote.dto.questions_dto

data class QuestionDTO(
    val accepted_answer_id: Int?,
    val answer_count: Int?,
    val content_license: String?,
    val creation_date: Int?,
    val is_answered: Boolean?,
    val last_activity_date: Int?,
    val last_edit_date: Int?,
    val link: String?,
    val owner: OwnerDTO?,
    val question_id: Int?,
    val score: Int?,
    val tags: List<String>?,
    val title: String?,
    val view_count: Int?
)