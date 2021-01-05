package com.marcocarrizales.githubreposapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class RepoApiModel(
    val id: Long,
    val name: String,
    val description: String,
    val owner: UserApiModel,
    @field:Json(name = "html_url") val htmlUrl: String,
    @field:Json(name = "stargazers_count") val stargazersCount: Int,
    @field:Json(name = "forks_count") val forksCount: Int,
    @field:Json(name = "contributors_url") val contributorsUrl: String
)