package com.stashinvest.stashchallenge

import android.app.Application
import androidx.lifecycle.Lifecycle
import com.stashinvest.stashchallenge.api.model.ImageResult
import com.stashinvest.stashchallenge.api.model.Metadata
import com.stashinvest.stashchallenge.ui.presenter.MainPresenter
import com.stashinvest.stashchallenge.ui.views.MainView
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


/**
 * @author Vishaan Tiwarie
 *
 * Test class to test the main fragment logic (MainPresenter)
 *
 * This unit test uses Mockito and Junit
 */
class MainPresenterTest {

    @Inject
    lateinit var mainPresenter: MainPresenter

    private var application = Mockito.mock(Application::class.java)
    private val view = Mockito.mock(MainView::class.java)
    private val lifecycle = Mockito.mock(Lifecycle::class.java)
    private val throwable = Mockito.mock(Throwable::class.java)
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        val component = DaggerTestComponent.builder()
                .testModule(TestModule(application))
                .build()

        Mockito.`when`(application.getString(R.string.base_url))
                .thenReturn("https://us-central1-stashandroidchallenge.cloudfunctions.net/api/")

        component.inject(this)

        mainPresenter.attachView(view, lifecycle)
    }

    @Test
    fun testFetchSearch() {
        val searchString = "stash"
        mainPresenter.fetchSearch(searchString, testScheduler, testScheduler)

        Mockito.`when`(application.getString(R.string.api_key))
                .thenReturn("764e736e08f1f1e8b587a62fc61383e2")

        testScheduler.triggerActions()
        Mockito.verify(view).showProgess(true)
        Mockito.verify(view, Mockito.never()).showError(throwable)
        Mockito.verify(view).showProgess(false)
    }

    @Test
    fun testFetchMetadata() {
        val id = "6780"
        val uri = "https://d1tn1yf5rnoefx.cloudfront.net/learnassets/images/blog_featured_image_thumbnails/learn_realestateetf.jpg"
        mainPresenter.fetchImageMetadata(id, uri, testScheduler, testScheduler)

        Mockito.`when`(application.getString(R.string.api_key))
                .thenReturn("764e736e08f1f1e8b587a62fc61383e2")

        testScheduler.triggerActions()
        Mockito.verify(view).showDialog(any(Metadata::class.java), Mockito.anyString(), any(ArrayList<ImageResult>().javaClass))
        Mockito.verify(view, Mockito.never()).showError(throwable)
    }

    @After
    fun tearDown() {
        mainPresenter.destroy()
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}