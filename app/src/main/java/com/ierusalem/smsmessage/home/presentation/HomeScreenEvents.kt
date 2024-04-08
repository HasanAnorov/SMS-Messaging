package com.ierusalem.smsmessage.home.presentation

interface HomeScreenEvents {
    data class OnAddClick(val number: String): HomeScreenEvents
    data class OnDeleteClick(val number: String): HomeScreenEvents
    data object OnContactsClick: HomeScreenEvents
}