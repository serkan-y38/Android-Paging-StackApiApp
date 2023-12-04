package com.yilmaz.stackapiapp.data.remote.mappers

import com.yilmaz.stackapiapp.data.remote.dto.questions_dto.OwnerDTO
import com.yilmaz.stackapiapp.data.remote.dto.questions_dto.QuestionDTO
import com.yilmaz.stackapiapp.domain.model.questions.Owner
import com.yilmaz.stackapiapp.domain.model.questions.Question


fun List<QuestionDTO>.toDomain(): List<Question> {
    return map {
        Question(
            it.question_id.toString(),
            it.link ?: "",
            it.owner?.toDomain() ?: Owner("", "", ""),
            it.tags ?: emptyList(),
            it.title ?: ""
        )
    }
}

fun OwnerDTO.toDomain(): Owner {
    return Owner(
        display_name ?: "",
        link ?: "",
        profile_image ?: "",
    )
}