package com.ierusalem.smsmessage.categories

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.ierusalem.smsmessage.MainViewModel
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme
import com.ierusalem.smsmessage.utils.Categories
import com.ierusalem.smsmessage.utils.Constants
import com.ierusalem.smsmessage.utils.PreferenceHelper


class CategoriesFragment : Fragment() {

    private val viewModel: MainViewModel = MainViewModel()
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferenceHelper = PreferenceHelper(context)
        loadCategories()

//        check this out later - worked here, can be useful for later usage
//        val listener = OnSharedPreferenceChangeListener { sharedPreferences, key ->
//                if (key == Constants.CATEGORIES_KEY) {
//                    //val newUsername = sharedPreferences.getString(key, "Default")
//                    // Update UI or perform necessary actions
//                    val defaultValue = Gson().toJson(Categories(listOf()))
//                    val stringCat = sharedPreferences.getString(key, defaultValue)
//                    val categoriesX = Gson().fromJson(stringCat, Categories::class.java)
//                    Log.d("ahi3646", "onAttach1: $categoriesX  $stringCat $key")
//                    //viewModel.loadCategories(categories)
//                }
//            }
//        preferenceHelper.getInstance().registerOnSharedPreferenceChangeListener(listener)
    }

    private fun loadCategories() {
        val categories = preferenceHelper.getCategories()
        viewModel.loadCategories(categories)
        Log.d("ahi3646", "onAttach : ${categories.list.size} ")
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
                            uiState = uiState,
                            onNavClick = { findNavController().popBackStack() },
                            onGroupDelete = { name ->
                                val categories = preferenceHelper.getCategories()
                                val temp = categories.list.find { it.name == name }
                                if (temp != null) {
                                    val categoriesList = categories.list.toMutableList().apply {
                                        remove(temp)
                                    }
                                    preferenceHelper.saveCategories(Categories(categoriesList))
                                    loadCategories()
                                }
                            },
                            onGroupClick = {
                                val bundle = bundleOf(Constants.CATEGORIES_KEY to it)
                                findNavController().navigate(
                                    R.id.action_categoriesFragment_to_groupContactsFragment,
                                    bundle
                                )
                            }
                        )
                    }
                }
            }
    }

}