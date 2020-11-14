package de.example.rxjavaunittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.data.repository.Repository
import de.example.rxjavaunittest.data.repository.RepositoryImpl
import de.example.rxjavaunittest.model.Post
import de.example.rxjavaunittest.viewmodel.MyViewModel
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

    private lateinit var repository: Repository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(api)
    }

    @Test
    fun testGetPost() {
        val post = Post(1, 1, "test test", "test text")
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)

        Mockito.`when`(repository.fetchPost()).thenReturn(Single.just(post))
        viewModel.getPost()
        val singlePost: Post? = viewModel.singlePost.value

        assertEquals(post, singlePost)
    }

    @Test
    fun testGetPostBody() {
        val post = Post(1, 1, "test test", "test text")
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)

        Mockito.`when`(repository.fetchPost()).thenReturn(Single.just(post))
        viewModel.getPost()
        val body: String? = viewModel.singlePost.value?.body

        assertEquals(post.body, body)
    }

    @Test
    fun testGetPostExpectedError() {
        val expectedError = Throwable()
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)

        Mockito.`when`(repository.fetchPost()).thenReturn(Single.error(expectedError))
        viewModel.getPost()
        val isError: Boolean? = viewModel.isError.value

        assertEquals(true, isError)
    }

    @Test
    fun testGetAllPosts() {
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)
        val posts = arrayListOf(
            Post(0, 0, "", ""),
            Post(1, 1, "1", "1")
        )

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.just(posts))
        viewModel.getAllPosts()
        val tempPosts: List<Post>? = viewModel.posts.value

        assertEquals(posts, tempPosts)
    }

    @Test
    fun testGetAllPostsSize() {
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)
        val posts = arrayListOf(
            Post(0, 0, "", ""),
            Post(1, 1, "1", "1")
        )

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.just(posts))
        viewModel.getAllPosts()
        val numberOfPosts: Int? = viewModel.posts.value?.size

        assertEquals(2, numberOfPosts)
    }

    @Test
    fun testGetAllPostsExpectedError() {
        val scheduler = TestScheduler()
        val viewModel = MyViewModel(repository, scheduler)
        val expectedError = Throwable()

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.error(expectedError))

        viewModel.getAllPosts()
        val isError: Boolean? = viewModel.isError.value

        assertEquals(true, isError)
    }

}
