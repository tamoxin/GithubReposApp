package com.marcocarrizales.githubreposapp.model

import com.squareup.moshi.Json

data class UserApiModel(
    val id: Long,
    val login: String,
    @field:Json(name = "avatar_url") val avatarUrl: String?
)