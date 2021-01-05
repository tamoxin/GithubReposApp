package com.marcocarrizales.githubreposapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcocarrizales.githubreposapp.databinding.RepoItemBinding
import com.marcocarrizales.githubreposapp.model.RepoApiModel
import com.squareup.picasso.Picasso

class RepoAdapter: RecyclerView.Adapter<RepoAdapter.RepoItemViewHolder>() {

    private val repos: MutableList<RepoApiModel> = mutableListOf()
    private lateinit var itemClickListener: OnItemClickListener

    inner class RepoItemViewHolder(private val binder: RepoItemBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind (repoItem: RepoApiModel, position: Int) {
            binder.apply {
                userName.text = repoItem.owner.login
                repoName.text = repoItem.name
                starCount.text = "${repoItem.stargazersCount}"
                forkCount.text = "${repoItem.forksCount}"
                Picasso.get().load(repoItem.owner.avatarUrl).into(avatar)
                binder.root.setOnClickListener {
                    itemClickListener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val binder = RepoItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        if (binder.root.context is OnItemClickListener)
            itemClickListener = binder.root.context as OnItemClickListener
        return RepoItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.bind(repos[position], position)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun updateRepos(items: List<RepoApiModel>) {
        repos.clear()
        repos.addAll(items)
        notifyDataSetChanged()
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}