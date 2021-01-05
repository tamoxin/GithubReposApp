package com.marcocarrizales.githubreposapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.marcocarrizales.githubreposapp.databinding.ActivityMainBinding
import com.marcocarrizales.githubreposapp.recyclerview.OnItemClickListener
import com.marcocarrizales.githubreposapp.ui.DetailsFragment
import com.marcocarrizales.githubreposapp.ui.MainFragment
import com.marcocarrizales.githubreposapp.viewmodel.GithubViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binder: ActivityMainBinding
    private val model: GithubViewModel by viewModel()
    private var queryTextChangedJob: Job? = null
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        setUpSearch(menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpSearch(menu: Menu) {
        val menuItem = menu.findItem(R.id.search)
        searchView = menuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var typedText = ""
            override fun onQueryTextSubmit(repo: String?): Boolean {
                if (repo.isNullOrEmpty())
                    return false

                if (repo == typedText) {
                    if (!searchView.isIconified)
                        searchView.onActionViewCollapsed()
                    return false
                }
                doSearch(repo)
                return false
            }

            override fun onQueryTextChange(repo: String?): Boolean {
                if (repo.isNullOrEmpty())
                    return false
                typedText = repo
                doSearch(repo)
                return false
            }

        })
    }

    private fun doSearch(repo: String) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
            println("async work started...")
            delay(1000)
            model.doSearch(repo)
            println("async work done!")
        }
    }

    override fun onItemClick(position: Int) {
        if (!searchView.isIconified)
            searchView.onActionViewCollapsed()
        val bundle = Bundle()
        bundle.putInt("position", position)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}