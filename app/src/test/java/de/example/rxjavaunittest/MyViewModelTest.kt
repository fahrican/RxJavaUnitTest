package de.example.rxjavaunittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class MyViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var api: JsonPlaceholderApi


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetPost() {
        val repository = Repository(api)
        val post = Post(1, 1, "test test", "test text")
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)

        Mockito.`when`(repository.fetchPost()).thenReturn(Single.just(post))
        viewModel.getPost()
        val singlePost: Post? = viewModel.singlePost.value

        assertEquals(post, singlePost)
    }

    @Test
    fun testGetAllPosts() {
        val repository = Repository(api)
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)
        val posts = arrayListOf(Post(0, 0, "", ""))

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.just(posts))
        viewModel.getAllPosts()

        val numberOfPosts: Int? = viewModel.posts.value
        assertEquals(1, numberOfPosts)
    }

}