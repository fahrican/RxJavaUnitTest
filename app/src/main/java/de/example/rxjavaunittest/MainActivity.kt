package de.example.rxjavaunittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val service: JsonPlaceholderApi = JsonPlaceholderApiService.getClient()
    private val repository = Repository(service)
    private val viewModel =  MyViewModel(repository, Schedulers.io())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        //viewModel.getAllPosts()
        viewModel.getPost()
    }

}