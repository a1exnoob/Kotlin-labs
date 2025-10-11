package com.example.appyaz

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appyaz.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationOnClickListener { returnToMainMenu() }

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        adapter = RecyclerViewAdapter(
            onItemClick = { model ->
                val intent = Intent(this, DescriptionActivity::class.java).apply {
                    putExtra("modelId", model.id)
                }
                startActivity(intent)
            },
            onDeleteClick = { model ->
                viewModel.removeModel(model.id)
            }
        )

        binding.recyclerViewUAZ.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.models.collectLatest { models ->
                adapter.submitList(models)
            }
        }
    }

    private fun returnToMainMenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val intent = Intent(this, EditActivity::class.java).apply {
                    putExtra("modelId", -1)
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}