package com.ierusalem.smsmessage.contacts.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.ierusalem.smsmessage.MainViewModel
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

class ContactsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onPermissionChanged(
            isGranted = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.state.collectAsStateWithLifecycle()

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.onPermissionChanged(isGranted)
                    }
                )

                LaunchedEffect(key1 = true) {
                    launcher.launch(Manifest.permission.READ_CONTACTS)
                }
                Log.d("ahi3646", "onCreateView: ${state.isReadContactsGranted} ")
                if (state.isReadContactsGranted) {
                    val contacts = mutableListOf<ContactItemModel>()
                    val cursor: Cursor = requireContext().contentResolver.query(
                        Phone.CONTENT_URI,
                        arrayOf(
                            ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            Phone.NUMBER,
                            ContactsContract.RawContacts.ACCOUNT_TYPE
                        ),
                        ContactsContract.RawContacts.ACCOUNT_TYPE + " <> 'google' ",
                        null, null
                    )!!
                    while (cursor.moveToNext()) {
                        if (cursor.getColumnIndex(Phone.DISPLAY_NAME) != -1 && cursor.getColumnIndex(
                                Phone.NUMBER
                            ) != -1
                        ) {
                            val name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))
                            val phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))
                            contacts.add(
                                ContactItemModel(
                                    contactName = name,
                                    phoneNumber = phoneNumber
                                )
                            )
                        }
                    }
                    cursor.close()
                    viewModel.loadContacts(contacts.distinctBy { it.phoneNumber })
                } else {
                    LaunchedEffect(key1 = true) {
                        launcher.launch(Manifest.permission.READ_CONTACTS)
                    }
                }

                SMSMessageTheme {
                    ContactsScreen(
                        uiState = state,
                        onSubmitClick = { contacts ->
                            viewModel.addNumberFromContacts(contacts)
                            findNavController().navigate(R.id.action_contactsFragment_to_homeFragment)
                        },
                        onNavClick = { findNavController().popBackStack() }
                    )
                }
            }
        }
    }

}