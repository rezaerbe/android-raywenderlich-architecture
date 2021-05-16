package com.erbe.mymvp.add

import com.erbe.mymvp.BaseTest
import com.erbe.mymvp.model.LocalDataSource
import com.erbe.mymvp.model.Movie
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddMoviePresenterTests : BaseTest() {

    @Mock
    private lateinit var mockActivity: AddMovieContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    lateinit var addMoviePresenter: AddMoviePresenter

    @Captor
    private lateinit var movieArgumentCaptor: ArgumentCaptor<Movie>

    @Before
    fun setUp() {
        addMoviePresenter =
            AddMoviePresenter(viewInterface = mockActivity, dataSource = mockDataSource)
    }

    @Test
    fun testAddMovieNoTitle() {
        //Invoke
        addMoviePresenter.addMovie("", "", "")

        //Verify
        Mockito.verify(mockActivity).displayError("Movie title cannot be empty")
    }

    @Test
    fun testAddMovieWithTitle() {

        //Invoke
        addMoviePresenter.addMovie(
            "The Lion King",
            "1994-05-07",
            "/bKPtXn9n4M4s8vvZrbw40mYsefB.jpg"
        )

        //Verify
        Mockito.verify(mockDataSource).insert(captureArg(movieArgumentCaptor))
        assertEquals("The Lion King", movieArgumentCaptor.value.title)


        Mockito.verify(mockActivity).returnToMain()
    }
}