package com.marcocarrizales.githubreposapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcocarrizales.githubreposapp.databinding.FragmentMainBinding
import com.marcocarrizales.githubreposapp.model.RepoApiModel
import com.marcocarrizales.githubreposapp.recyclerview.RepoAdapter
import com.marcocarrizales.githubreposapp.viewmodel.GithubViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    private lateinit var binder: FragmentMainBinding
    private val reposAdapter: RepoAdapter by inject()
    private val model: GithubViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binder = FragmentMainBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeAppState()
    }

    private fun observeAppState() {
        model.state.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is AppStateLoaded -> showLoadedState(it.repos)
                    is AppStateLoading -> showLoadingState()
                    is AppStateError -> showErrorState(it.error)
                }
            }
        })
    }

    private fun showLoadedState(repos: List<RepoApiModel>) {
        binder.reposList.visibility = View.VISIBLE
        binder.progressBar.visibility = View.GONE
        binder.errorTextView.visibility = View.GONE
        reposAdapter.updateRepos(repos)
    }

    private fun showErrorState(error: String) {
        binder.errorTextView.text = error
        binder.errorTextView.visibility = View.VISIBLE
        binder.reposList.visibility = View.GONE
        binder.progressBar.visibility = View.GONE
    }

    private fun showLoadingState() {
        binder.progressBar.visibility = View.VISIBLE
        binder.reposList.visibility = View.GONE
        binder.errorTextView.visibility = View.GONE
    }

    private fun setupRecycler() {
        binder.reposList.layoutManager = LinearLayoutManager(context)
        binder.reposList.adapter = reposAdapter
    }
}