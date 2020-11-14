package de.example.rxjavaunittest.data.repository

import de.example.rxjavaunittest.model.Post
import io.reactivex.Single

interface Repository {

    fun fetchPost(): Single<Post>

    fun fetchAllPosts(): Single<List<Post>>

}
