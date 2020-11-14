package de.example.rxjavaunittest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.example.rxjavaunittest.data.repository.Repository
import de.example.rxjavaunittest.model.Post
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MyViewModel(
    private val repository: Repository,
    private var schedulers: Scheduler
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _singlePost by lazy { MutableLiveData<Post>() }
    val singlePost: LiveData<Post>
        get() = _singlePost

    private val _posts by lazy { MutableLiveData<List<Post>>() }
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _isError by lazy { MutableLiveData<Boolean>() }
    val isError: LiveData<Boolean>
        get() = _isError

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
                        Timber.d("getPost() $it")
                    },
                    {
                        Timber.e("getPost() network error: ${it.message}")
                        _isError.postValue(true)
                    })
        )
    }


    fun getAllPosts() {
        compositeDisposable.add(
            repository.fetchAllPosts()
                .subscribeOn(schedulers)
                .subscribe(
                    {
                        _posts.postValue(it)
                    },
                    {
                        Timber.e("fetchAllPosts() network error: ${it.message}")
                        _isError.postValue(true)
                    }
                ))
    }

}