package com.marcocarrizales.githubreposapp.api

import com.marcocarrizales.githubreposapp.model.ResultsApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/repositories")
    fun reposList(@Query("q") repo: String): Call<ResultsApiModel>
}