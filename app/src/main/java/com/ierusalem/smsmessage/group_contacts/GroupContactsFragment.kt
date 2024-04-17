package com.ierusalem.smsmessage.group_contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme
import com.ierusalem.smsmessage.utils.Constants
import com.ierusalem.smsmessage.utils.PreferenceHelper

class GroupContactsFragment : Fragment() {

    private val viewModel: GroupContactsViewModel = GroupContactsViewModel()
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferenceHelper = PreferenceHelper(context)
        val categoryName = arguments?.getString(Constants.CATEGORIES_KEY, "")
        if (!categoryName.isNullOrEmpty()) {
            val categories = preferenceHelper.getCategories()
            val category = categories.list.findLast { it.name == categoryName }
            viewModel.loadContacts(category!!)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.can_t_find_group_contacts), Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.state.collectAsStateWithLifecycle()
                SMSMessageTheme {
                    GroupContactsScreen(
                        groupName = state.category.name,
                        contacts = state.category.contacts,
                        onNavClick = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }


}