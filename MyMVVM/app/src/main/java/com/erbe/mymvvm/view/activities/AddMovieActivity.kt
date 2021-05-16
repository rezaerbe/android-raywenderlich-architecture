package com.erbe.mymvvm.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.erbe.mymvvm.R
import com.erbe.mymvvm.action
import com.erbe.mymvvm.databinding.ActivityAddBinding
import com.erbe.mymvvm.snack
import com.erbe.mymvvm.viewmodel.AddViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.intentFor

class AddMovieActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private lateinit var viewModel: AddViewModel

    override fun getToolbarInstance(): Toolbar? = toolbar

    fun searchMovieClicked(view: View) {
        if (titleEditText.text.toString().isNotBlank()) {
            startActivity(intentFor<SearchMovieActivity>("title" to titleEditText.text.toString()))
        } else {
            showMessage(getString(R.string.enter_title))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityAddBinding>(this, R.layout.activity_add)
        viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        binding.viewModel = viewModel
        configureLiveDataObservers()
    }

    private fun showMessage(msg: String) {
        addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }

    private fun configureLiveDataObservers() {
        viewModel.getSaveLiveData().observe(this, Observer { saved ->
            saved?.let {
                if (saved) {
                    finish()
                } else {
                    showMessage(getString(R.string.title_date_message))
                }
            }
        })
    }
}