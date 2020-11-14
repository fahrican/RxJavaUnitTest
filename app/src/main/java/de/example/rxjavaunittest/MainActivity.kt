package de.example.rxjavaunittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.example.rxjavaunittest.databinding.ActivityMainBinding
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val service: JsonPlaceholderApi = JsonPlaceholderApiService.getClient()
    private val repository = RepositoryImpl(service)
    private val viewModel = MyViewModel(repository, Schedulers.io())

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