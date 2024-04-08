package com.ierusalem.smsmessage.contacts.data


data class ContactItemModel(
    val contactName: String,
    val phoneNumber: String,
    var isSelected: Boolean = false
)