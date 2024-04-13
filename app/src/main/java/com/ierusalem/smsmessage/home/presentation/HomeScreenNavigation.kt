package com.ierusalem.smsmessage.home.presentation

import com.ierusalem.smsmessage.utils.Category

interface HomeScreenNavigation {
    data object OpenContacts: HomeScreenNavigation
    data object OpenCategories: HomeScreenNavigation
    data class AddCategory(val category: Category): HomeScreenNavigation
}