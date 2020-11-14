package de.example.rxjavaunittest.data.repository

import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.di.DaggerAppComponent
import de.example.rxjavaunittest.model.Post
import io.reactivex.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: JsonPlaceholderApi) : Repository {

    init {
        DaggerAppComponent.create().inject(this)
    }

    override fun fetchPost(): Single<Post> = api.getPost()

    override fun fetchAllPosts(): Single<List<Post>> = api.getAllPosts()

}

