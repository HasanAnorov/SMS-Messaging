package com.ierusalem.smsmessage.contacts.domain

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.smsmessage.contacts.data.ContactItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactsViewModel : ViewModel() {

    private var _state: MutableStateFlow<ContactsUiState> = MutableStateFlow(ContactsUiState())
    val state = _state.asStateFlow()

    fun loadContacts(contacts: List<ContactItemModel>) {
        _state.update {
            it.copy(
                contacts = contacts
            )
        }
    }

    fun onPermissionChanged(isGranted: Boolean) {
        _state.update {
            it.copy(
                isReadContactsReadGranted = isGranted
            )
        }
    }

}

@Immutable
data class ContactsUiState(
    val isReadContactsReadGranted: Boolean = false,
    val contacts: List<ContactItemModel> = listOf()
)