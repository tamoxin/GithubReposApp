package com.marcocarrizales.githubreposapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcocarrizales.githubreposapp.databinding.FragmentDetailsBinding
import com.marcocarrizales.githubreposapp.viewmodel.GithubViewModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.properties.Delegates


class DetailsFragment : Fragment() {
    private val model: GithubViewModel by sharedViewModel()
    private lateinit var binder: FragmentDetailsBinding
    private var position by Delegates.notNull<Int>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            position = it.getInt("position")
        }
        binder = FragmentDetailsBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        if (model.state.value is AppStateLoaded){
            val repo = (model.state.value as AppStateLoaded).repos[position]
            binder.apply {
                userName.text = repo.owner.login
                repoName.text = repo.name
                starCount.text = "${repo.stargazersCount}"
                forkCount.text = "${repo.forksCount}"
                Picasso.get().load(repo.owner.avatarUrl).into(avatar)
                repoButton.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
                    startActivity(browserIntent)
                }
            }
        }
    }
}