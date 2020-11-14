package de.example.rxjavaunittest.data.repository

import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.model.Post
import io.reactivex.Single

class RepositoryImpl(private val api: JsonPlaceholderApi) : Repository {

    override fun fetchPost(): Single<Post> = api.getPost()

    override fun fetchAllPosts(): Single<List<Post>> = api.getAllPosts()

}

