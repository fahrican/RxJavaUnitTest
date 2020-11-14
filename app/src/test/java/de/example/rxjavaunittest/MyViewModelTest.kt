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
    private val scheduler = TestScheduler()
    private val post = Post(1, 1, "test test", "test text")
    private lateinit var classUnderTest: MyViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(api)
        classUnderTest = MyViewModel(repository, scheduler)
    }

    @Test
    fun getPost() {
        Mockito.`when`(repository.fetchPost()).thenReturn(Single.just(post))
        classUnderTest.getPost()
        val singlePost: Post? = classUnderTest.singlePost.value

        assertEquals(post, singlePost)
    }

    @Test
    fun getPostBody() {
        Mockito.`when`(repository.fetchPost()).thenReturn(Single.just(post))
        classUnderTest.getPost()
        val body: String? = classUnderTest.singlePost.value?.body

        assertEquals(post.body, body)
    }

    @Test
    fun getPostExpectedError() {
        val expectedError = Throwable()

        Mockito.`when`(repository.fetchPost()).thenReturn(Single.error(expectedError))
        classUnderTest.getPost()
        val isError: Boolean? = classUnderTest.isError.value

        assertEquals(true, isError)
    }

    @Test
    fun getAllPosts() {
        val posts = arrayListOf(
            Post(0, 0, "", ""),
            post
        )

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.just(posts))
        classUnderTest.getAllPosts()
        val tempPosts: List<Post>? = classUnderTest.posts.value

        assertEquals(posts, tempPosts)
    }

    @Test
    fun getAllPostsSize() {
        val posts = arrayListOf(
            Post(0, 0, "", ""),
            post
        )

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.just(posts))
        classUnderTest.getAllPosts()
        val numberOfPosts: Int? = classUnderTest.posts.value?.size

        assertEquals(2, numberOfPosts)
    }

    @Test
    fun getAllPostsExpectedError() {
        val expectedError = Throwable()

        Mockito.`when`(repository.fetchAllPosts()).thenReturn(Single.error(expectedError))
        classUnderTest.getAllPosts()
        val isError: Boolean? = classUnderTest.isError.value

        assertEquals(true, isError)
    }

}
