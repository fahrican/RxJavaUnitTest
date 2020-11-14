package de.example.rxjavaunittest

import de.example.rxjavaunittest.data.networking.JsonPlaceholderApi
import de.example.rxjavaunittest.data.repository.Repository
import de.example.rxjavaunittest.data.repository.RepositoryImpl
import de.example.rxjavaunittest.model.Post
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class RepositoryImplTest {

    @Mock
    lateinit var api: JsonPlaceholderApi

    private lateinit var classUnderTest: Repository
    private val post = Post(1, 1, "test test", "test text")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        classUnderTest = RepositoryImpl(api)
    }


    @Test
    fun fetchPost() {
        `when`(api.getPost()).thenReturn(Single.just(post))
        classUnderTest.fetchPost().test().assertValue(post)
    }

    @Test
    fun fetchPostTitle() {
        val post = Post(1, 1, "test test", "test text")
        `when`(api.getPost()).thenReturn(Single.just(post))
        classUnderTest.fetchPost().test().assertValue { it.title == post.title }
    }

    @Test
    fun fetchPostExpectedError() {
        val expectedError = Throwable()
        `when`(api.getPost()).thenReturn(Single.error(expectedError))
        classUnderTest.fetchPost().test().assertError(expectedError)
    }

    @Test
    fun fetchAllPosts() {
        val posts = arrayListOf<Post>()
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        classUnderTest.fetchAllPosts().test().assertValue(posts)
    }

    @Test
    fun fetchAllPostsSize() {
        val posts = arrayListOf(
            post,
            Post(2, 2, "title2", "text2")
        )
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        classUnderTest.fetchAllPosts().test().assertValue { it.isNotEmpty() }
    }

    @Test
    fun fetchAllPostsExpectedError() {
        val expectedError = Throwable()
        `when`(api.getAllPosts()).thenReturn(Single.error(expectedError))
        classUnderTest.fetchAllPosts().test().assertError(expectedError)
    }

}