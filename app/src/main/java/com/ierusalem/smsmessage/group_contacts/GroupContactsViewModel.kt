package com.ierusalem.smsmessage.group_contacts

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.ierusalem.smsmessage.utils.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GroupContactsViewModel:ViewModel() {

    private var _state: MutableStateFlow<GroupContactsState> = MutableStateFlow(GroupContactsState())
    val state = _state.asStateFlow()

    fun loadContacts(category: Category){
        _state.update {
            it.copy(
                category = category
            )
        }
    }

}

@Immutable
data class GroupContactsState(
    val category: Category = Category("", listOf())
)