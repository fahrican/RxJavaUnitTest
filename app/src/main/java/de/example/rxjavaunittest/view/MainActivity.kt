package de.example.rxjavaunittest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApiService
import de.example.rxjavaunittest.viewmodel.MyViewModel
import de.example.rxjavaunittest.data.repository.RepositoryImpl
import de.example.rxjavaunittest.databinding.ActivityMainBinding
import de.example.rxjavaunittest.di.ViewModelFactory
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val service: JsonPlaceholderApi = JsonPlaceholderApiService.getClient()
    private val repository = RepositoryImpl(service)
    private val schedulers = Schedulers.io()
    private val viewModel: MyViewModel by viewModels { ViewModelFactory(repository, schedulers) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.getPost()
        viewModel.getAllPosts()
        observeLiveData()
    }

    private fun observeLiveData() {
        observePost()
        observePosts()
    }

    private fun observePost() {
        viewModel.singlePost.observe(this, { post ->
            binding.postTitle.text = "Title: ${post.title}"
        })
    }

    private fun observePosts() {
        viewModel.posts.observe(this, { posts ->
            binding.numberOfPosts.text = "Number of posts: ${posts.size}"
        })
    }

}