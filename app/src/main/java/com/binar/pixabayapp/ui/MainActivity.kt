package com.binar.pixabayapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.pixabayapp.R
import com.binar.pixabayapp.databinding.ActivityMainBinding
import com.binar.pixabayapp.provider.ServiceLocator
import com.binar.pixabayapp.utils.viewModelFactory
import com.binar.pixabayapp.wrapper.Resource

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModelFactory {
        MainViewModel(ServiceLocator.provideRepository(applicationContext))
    }

    private val adapter: PostAdapter by lazy {
        PostAdapter {
            Toast.makeText(this, "this photo has: ${it.likes} likes", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initList()
        initView()
        observeData()
    }

    private fun initView() {
        binding.tvEmpty.text = getString(R.string.text_search_guide)
        binding.tvEmpty.isVisible = true
    }

    private fun observeData() {
        viewModel.searchResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbPost.isVisible = true
                    binding.rvPost.isVisible = false
                    binding.tvEmpty.isVisible = false
                    binding.tvError.isVisible = false
                }
                is Resource.Error -> {
                    adapter.clearItems()
                    binding.pbPost.isVisible = false
                    binding.rvPost.isVisible = false
                    binding.tvEmpty.isVisible = false
                    binding.tvError.isVisible = true
                    it.exception?.message?.let { errorMessage ->
                        binding.tvError.text = errorMessage
                    }
                }
                is Resource.Empty -> {
                    adapter.clearItems()
                    binding.pbPost.isVisible = false
                    binding.rvPost.isVisible = false
                    binding.tvError.isVisible = false
                    binding.tvEmpty.isVisible = true
                    binding.tvEmpty.text = getString(R.string.text_empty_state)
                }
                is Resource.Success -> {
                    it.payload?.posts?.let { data -> adapter.setItems(data) }
                    binding.pbPost.isVisible = false
                    binding.rvPost.isVisible = true
                    binding.tvEmpty.isVisible = false
                    binding.tvError.isVisible = false
                }
            }
        }
    }

    private fun initList() {
        binding.rvPost.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.menu_search_bar)
        setupSearchView(search)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchView(search: MenuItem?) {
        val searchView = search?.actionView as SearchView
        searchView.queryHint = getString(R.string.title_search)
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchPost(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        searchView.setOnCloseListener {
            searchView.onActionViewCollapsed()
            initView()
            adapter.clearItems()
            binding.rvPost.isVisible = false
            true
        }
    }

}