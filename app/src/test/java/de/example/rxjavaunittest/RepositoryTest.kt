package de.example.rxjavaunittest

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class RepositoryTest {

    @Mock
    lateinit var api: JsonPlaceholderApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFetchPost1() {
        val repository = Repository(api)
        val post = Post(1, 1, "test test", "test text")
        `when`(api.getPost()).thenReturn(Single.just(post))
        repository.fetchPost().test().assertValue(post)
    }

    @Test
    fun testFetchPost1Title() {
        val repository = Repository(api)
        val post = Post(1, 1, "test test", "test text")
        `when`(api.getPost()).thenReturn(Single.just(post))
        repository.fetchPost().test().assertValue { it.title == post.title }
    }

    @Test
    fun testFetchPost1ExpectedError() {
        val repository = Repository(api)
        val expectedError = Throwable()
        `when`(api.getPost()).thenReturn(Single.error(expectedError))
        repository.fetchPost().test().assertError(expectedError)
    }

    @Test
    fun testFetchAllPosts() {
        val repository = Repository(api)
        val posts = arrayListOf<Post>()
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        repository.fetchAllPosts().test().assertValue(posts)
    }

    @Test
    fun testFetchAllPostsSize() {
        val repository = Repository(api)
        val posts = arrayListOf(
            Post(1, 1, "test test", "test text"),
            Post(2, 2, "title2", "text2")
        )
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        repository.fetchAllPosts().test().assertValue { it.isNotEmpty() }
    }

    @Test
    fun testFetchAllPostsExpectedError() {
        val repository = Repository(api)
        val expectedError = Throwable()
        `when`(api.getAllPosts()).thenReturn(Single.error(expectedError))
        repository.fetchAllPosts().test().assertError(expectedError)
    }

}