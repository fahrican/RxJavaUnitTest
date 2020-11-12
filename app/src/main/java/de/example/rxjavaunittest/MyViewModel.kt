package de.example.rxjavaunittest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyViewModel(
    private val repository: Repository,
    private var schedulers: Scheduler
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _singlePost by lazy { MutableLiveData<Post>() }
    val singlePost: LiveData<Post>
        get() = _singlePost

    private val _posts by lazy { MutableLiveData<Int>() }
    val posts: LiveData<Int>
        get() = _posts


    init {
        schedulers = Schedulers.io()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getPost() {
        compositeDisposable.add(
            repository.fetchPost()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _singlePost.postValue(it)
                        //Log.d("getPost()", "$it")
                    },
                    {
                        Log.e("getPost()", "Network error: ${it.message}")
                    })
        )
    }


    fun getAllPosts() {
        compositeDisposable.add(
            repository.fetchAllPosts()
                .subscribeOn(schedulers)
                .subscribe(
                    {
                        _posts.postValue(it.size)
                    },
                    {
                        Log.e("fetchAllPosts()", "Network error: ${it.message}")
                    }
                ))
    }

}