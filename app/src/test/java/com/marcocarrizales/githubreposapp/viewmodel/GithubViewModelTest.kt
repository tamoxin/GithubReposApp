package com.marcocarrizales.githubreposapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.marcocarrizales.githubreposapp.api.ApiWebService
import com.marcocarrizales.githubreposapp.model.RepoApiModel
import com.marcocarrizales.githubreposapp.ui.AppStateLoaded
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GithubViewModel
    private lateinit var repos: MutableList<RepoApiModel>
    @Before
    fun setUp() {
        viewModel = GithubViewModel(ApiWebService())
        viewModel.state.observeForever {
            if (it is AppStateLoaded && it.repos.isNotEmpty())
                repos.addAll(it.repos)
        }
        viewModel.doSearch("android")
    }

    @Test
    fun `loaded state contains repos`() {
        assertThat(repos.size).isGreaterThan(0)
    }
}