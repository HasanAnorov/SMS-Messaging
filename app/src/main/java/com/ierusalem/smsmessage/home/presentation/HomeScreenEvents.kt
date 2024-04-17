package com.ierusalem.smsmessage.home.presentation

import com.ierusalem.smsmessage.PhoneNumber

interface HomeScreenEvents {
    data class OnAddClick(val phoneNumber: PhoneNumber): HomeScreenEvents
    data class OnMessageChanged(val message: String): HomeScreenEvents
    data class OnAddCategory(val name:String): HomeScreenEvents
    data class OnDeleteClick(val phoneNumber: PhoneNumber): HomeScreenEvents
    data object OnContactsClick: HomeScreenEvents
    data object OnCategoriesClick: HomeScreenEvents
}