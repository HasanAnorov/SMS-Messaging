package com.ierusalem.smsmessage.categories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.ierusalem.smsmessage.MainViewModel
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme
import com.ierusalem.smsmessage.utils.PreferenceHelper

class CategoriesFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel()
    private lateinit var preferenceHelper : PreferenceHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferenceHelper = PreferenceHelper(context)
        val categories = preferenceHelper.getCategories()
        viewModel.loadCategories(categories)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
            .apply {
                setContent {
                    val uiState by viewModel.state.collectAsStateWithLifecycle()
                    SMSMessageTheme {
                        CategoriesScreen(
                            onNavClick = { findNavController().popBackStack() },
                            uiState = uiState
                        )
                    }
                }
            }
    }

}