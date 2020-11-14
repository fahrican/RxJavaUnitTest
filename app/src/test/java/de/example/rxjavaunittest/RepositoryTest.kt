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

    private lateinit var repository: Repository
    private val post = Post(1, 1, "test test", "test text")

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = Repository(api)
    }


    @Test
    fun testFetchPost1() {
        `when`(api.getPost()).thenReturn(Single.just(post))
        repository.fetchPost().test().assertValue(post)
    }

    @Test
    fun testFetchPost1Title() {
        val post = Post(1, 1, "test test", "test text")
        `when`(api.getPost()).thenReturn(Single.just(post))
        repository.fetchPost().test().assertValue { it.title == post.title }
    }

    @Test
    fun testFetchPost1ExpectedError() {
        val expectedError = Throwable()
        `when`(api.getPost()).thenReturn(Single.error(expectedError))
        repository.fetchPost().test().assertError(expectedError)
    }

    @Test
    fun testFetchAllPosts() {
        val posts = arrayListOf<Post>()
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        repository.fetchAllPosts().test().assertValue(posts)
    }

    @Test
    fun testFetchAllPostsSize() {
        val posts = arrayListOf(
            post,
            Post(2, 2, "title2", "text2")
        )
        `when`(api.getAllPosts()).thenReturn(Single.just(posts))
        repository.fetchAllPosts().test().assertValue { it.isNotEmpty() }
    }

    @Test
    fun testFetchAllPostsExpectedError() {
        val expectedError = Throwable()
        `when`(api.getAllPosts()).thenReturn(Single.error(expectedError))
        repository.fetchAllPosts().test().assertError(expectedError)
    }

}