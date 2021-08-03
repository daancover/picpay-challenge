package com.picpay.desafio.android.ui.activity

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.picpay.desafio.android.FileReader
import com.picpay.desafio.android.R
import com.picpay.desafio.android.RecyclerViewMatchers
import com.picpay.desafio.android.di.testNetworkModule
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


class MainActivityTest : KoinTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val okHttpClient by inject<OkHttpClient>()

    @Before
    fun setup() {
        server.start(serverPort)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                okHttpClient
            )
        )
    }

    @BeforeTest
    fun setupTest() {
        loadKoinModules(testNetworkModule)

        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }
    }

    @AfterTest
    fun tearDownTest() {
        unloadKoinModules(testNetworkModule)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val request = server.takeRequest()
            assertEquals("/users", request.path)

            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        launchActivity<MainActivity>().apply {
            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                0,
                withText("Eduardo Santos")
            )
        }
    }

    companion object {
        private const val serverPort = 8080

        private val successResponse by lazy {
            MockResponse()
                .setResponseCode(200)
                .setBody(FileReader.readStringFromFile("users_success_response.json"))
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }
}