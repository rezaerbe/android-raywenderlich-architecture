package com.erbe.mymvp.search

import com.erbe.mymvp.BaseTest
import com.erbe.mymvp.RxImmediateSchedulerRule
import com.erbe.mymvp.model.Movie
import com.erbe.mymvp.model.RemoteDataSource
import com.erbe.mymvp.model.TmdbResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private val mockDataSource = RemoteDataSource()

    lateinit var searchPresenter: SearchPresenter

    @Before
    fun setUp() {
        searchPresenter = SearchPresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testSearchMovie() {
        //Set up
        val myDummyResponse = dummyResponse
        Mockito.doReturn(Observable.just(myDummyResponse)).`when`(mockDataSource)
            .searchResultsObservable(anyString())

        //Invoke
        searchPresenter.getSearchResults("The Lion King")

        //Assert
        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Test
    fun testSearchMovieError() {
        //Set up
        Mockito.doReturn(
            Observable.error<Throwable>(Throwable("Something went wrong"))
        ).`when`(mockDataSource).searchResultsObservable(anyString())

        //Invoke
        searchPresenter.getSearchResults("The Lion King")

        //Assert
        Mockito.verify(mockActivity).displayError("Error fetching Movie Data")
    }

    private val dummyResponse: TmdbResponse
        get() {
            val dummyMovieList = ArrayList<Movie>()
            dummyMovieList.add(Movie("Title1", "ReleaseDate1", "PosterPath1"))
            dummyMovieList.add(Movie("Title2", "ReleaseDate2", "PosterPath2"))
            dummyMovieList.add(Movie("Title3", "ReleaseDate3", "PosterPath3"))
            dummyMovieList.add(Movie("Title4", "ReleaseDate4", "PosterPath4"))

            return TmdbResponse(1, 4, 5, dummyMovieList)
        }
}