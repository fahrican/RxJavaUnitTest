package de.example.rxjavaunittest

import io.reactivex.Single

class Repository(private val api: JsonPlaceholderApi) {

    fun fetchPost(): Single<Post> = api.getPost()

    fun fetchAllPosts(): Single<List<Post>> = api.getAllPosts()

}