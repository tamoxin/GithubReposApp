package com.marcocarrizales.githubreposapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcocarrizales.githubreposapp.api.ApiInterface
import com.marcocarrizales.githubreposapp.api.ApiWebService
import com.marcocarrizales.githubreposapp.model.ResultsApiModel
import com.marcocarrizales.githubreposapp.ui.AppState
import com.marcocarrizales.githubreposapp.ui.AppStateError
import com.marcocarrizales.githubreposapp.ui.AppStateLoaded
import com.marcocarrizales.githubreposapp.ui.AppStateLoading
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel(private val api: ApiWebService) : ViewModel() {
    private val _state = MutableLiveData<AppState>()
    val state: LiveData<AppState> = _state

    init {
        _state.value = AppStateLoaded(emptyList())
    }

    fun doSearch(repoName: String) {
        if (repoName.isNotEmpty())
            _state.value = AppStateLoading
        val webInterface: ApiInterface = api.getWebService()
        val call = webInterface.reposList(repoName)
        call.enqueue(object : Callback<ResultsApiModel> {
            override fun onResponse(call: Call<ResultsApiModel>, response: Response<ResultsApiModel>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        _state.value = AppStateLoaded(response.body()!!.items)
                }
                else _state.value = AppStateError(response.errorBody().toString() ?: "There was an error")
            }

            override fun onFailure(call: Call<ResultsApiModel>, t: Throwable) {
                _state.value = AppStateError(t.message ?: "There was an error")
            }

        })

    }
}