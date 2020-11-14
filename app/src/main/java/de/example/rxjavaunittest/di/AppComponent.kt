package de.example.rxjavaunittest.di

import dagger.Component
import de.example.rxjavaunittest.data.repository.RepositoryImpl
import de.example.rxjavaunittest.view.MainActivity
import de.example.rxjavaunittest.viewmodel.MyViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(repo: RepositoryImpl)

    fun inject(viewModel: MyViewModel)

    fun inject(mainActivity: MainActivity)

}