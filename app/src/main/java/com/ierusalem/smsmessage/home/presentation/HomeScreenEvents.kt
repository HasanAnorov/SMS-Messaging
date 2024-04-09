package com.ierusalem.smsmessage.home.presentation

import com.ierusalem.smsmessage.PhoneNumber

interface HomeScreenEvents {
    data class OnAddClick(val phoneNumber: PhoneNumber): HomeScreenEvents
    data class OnDeleteClick(val phoneNumber: PhoneNumber): HomeScreenEvents
    data object OnContactsClick: HomeScreenEvents
}