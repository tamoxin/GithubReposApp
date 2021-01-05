package com.marcocarrizales.githubreposapp

import com.marcocarrizales.githubreposapp.api.ApiWebService
import com.marcocarrizales.githubreposapp.recyclerview.RepoAdapter
import com.marcocarrizales.githubreposapp.viewmodel.GithubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    single { ApiWebService() }

    factory { RepoAdapter() }

    viewModel { GithubViewModel(get()) }
}