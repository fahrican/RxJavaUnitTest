package de.example.rxjavaunittest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.example.rxjavaunittest.data.repository.Repository
import de.example.rxjavaunittest.viewmodel.MyViewModel
import io.reactivex.Scheduler

class ViewModelFactory(repo: Repository, scheduler: Scheduler) : ViewModelProvider.Factory {

    private val mRepo: Repository = repo
    private val mScheduler: Scheduler = scheduler


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(mRepo, mScheduler) as T
    }

}