package com.marcocarrizales.githubreposapp.ui

import com.marcocarrizales.githubreposapp.model.RepoApiModel

sealed class AppState
object AppStateLoading : AppState()
data class AppStateLoaded(val repos: List<RepoApiModel>) : AppState()
data class AppStateError(val error: String) : AppState()