package de.example.rxjavaunittest

import io.reactivex.Single
import retrofit2.http.GET

interface JsonPlaceholderApi {

    @GET("posts/1")
    fun getPost(): Single<Post>

    @GET("posts")
    fun getAllPosts(): Single<List<Post>>

}