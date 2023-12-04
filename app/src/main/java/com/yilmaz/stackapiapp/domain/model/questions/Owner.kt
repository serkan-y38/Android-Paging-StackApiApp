package com.yilmaz.stackapiapp.domain.model.questions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val ownerDisplayName: String,
    val ownerLink: String,
    val ownerProfileImage: String,
) : Parcelable
