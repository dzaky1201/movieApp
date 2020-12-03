package com.example.movie.ui.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movie.R
import com.example.movie.model.Status
import com.example.movie.utils.toAge
import com.example.movie.utils.toStandardDateFormat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_landing.*
import javax.inject.Inject
import kotlin.properties.Delegates

private const val CAST_ID = "castId"

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {
    private var castId by Delegates.notNull<Long>()

    @Inject
    lateinit var aboutViewModelFactory: AboutViewModel.AssistedFactory

    private val aboutViewModel: AboutViewModel by viewModels {
        AboutViewModel.provideFactory(
            aboutViewModelFactory,
            castId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            castId = it.getLong(CAST_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about_srl.isEnabled = false

        aboutViewModel.cast.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { cast ->
                        tvBirthday.text =
                            if (!cast.birthday?.toStandardDateFormat().isNullOrEmpty()) cast.birthday?.toStandardDateFormat() else getString(R.string.dash)
                        tvAge.text =
                            if (!cast.birthday?.toAge().isNullOrEmpty()) cast.birthday?.toAge() else getString(R.string.dash)
                        tvPlaceOfBirth.text =
                            if (!cast.placeOfBirth.isNullOrEmpty()) cast.placeOfBirth else getString(
                                R.string.dash
                            )
                        tvDepartment.text =
                            if (!cast.department.isNullOrEmpty()) cast.department else getString(R.string.dash)
                        tvBioghraphy.text =
                            if (!cast.biography.isNullOrEmpty()) cast.biography else getString(R.string.dash)
                        tvKnownAs.text =
                            if (!cast.knownAs.isNullOrEmpty()) cast.knownAs.joinToString(", ") else getString(
                                R.string.dash
                            )

                        cast.deathday?.let { deathday->
                            tvDeadDay.text = deathday.toStandardDateFormat()
                            llDeathDay.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        about_srl.isRefreshing = isLoading

        if (!isLoading) {
            nsv.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(castId: Long) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putLong(CAST_ID, castId)
                }
            }
    }
}