package de.example.rxjavaunittest

import io.reactivex.Single

class RepositoryImpl(private val api: JsonPlaceholderApi) : Repository {

    override fun fetchPost(): Single<Post> = api.getPost()

    override fun fetchAllPosts(): Single<List<Post>> = api.getAllPosts()

}

