package com.ierusalem.smsmessage.contacts.presentation

data class ContactItemModel(
    val contactName: String,
    val phoneNumber: String,
    var isSelected: Boolean = false
)