package de.example.rxjavaunittest

import io.reactivex.Single

interface Repository {

    fun fetchPost(): Single<Post>

    fun fetchAllPosts(): Single<List<Post>>

}
