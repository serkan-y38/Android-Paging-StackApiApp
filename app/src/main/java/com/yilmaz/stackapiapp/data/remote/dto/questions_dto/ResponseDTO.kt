package com.yilmaz.stackapiapp.data.remote.dto.questions_dto

data class ResponseDTO(
    val has_more: Boolean?,
    val items: List<QuestionDTO>?,
    val quota_max: Int?,
    val quota_remaining: Int?
)