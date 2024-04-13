package com.ierusalem.smsmessage.home.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ierusalem.smsmessage.MainViewModel
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.components.PermissionDialog
import com.ierusalem.smsmessage.ui.components.SendSMSMessageTextProvider
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme
import com.ierusalem.smsmessage.utils.Categories
import com.ierusalem.smsmessage.utils.PreferenceHelper
import com.ierusalem.smsmessage.utils.executeWithLifecycle
import com.ierusalem.smsmessage.utils.openAppSettings

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var preferenceHelper: PreferenceHelper

    private fun checkRequest(): Boolean = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.SEND_SMS
    ) == PackageManager.PERMISSION_GRANTED

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferenceHelper = PreferenceHelper(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            consumeWindowInsets = false
            setContent {

                val state by viewModel.state.collectAsState()
                SMSMessageTheme {
                    val dialogQueue = viewModel.visiblePermissionDialogQueue
                    val sendSMSMessagesPermissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            viewModel.onPermissionResult(
                                permission = Manifest.permission.SEND_SMS,
                                isGranted = isGranted
                            )
                        }
                    )

                    dialogQueue
                        .reversed()
                        .forEach { permission ->
                            PermissionDialog(
                                permissionTextProvider = when (permission) {
                                    Manifest.permission.SEND_SMS -> {
                                        SendSMSMessageTextProvider()
                                    }

                                    else -> return@forEach
                                },
                                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                    permission
                                ),
                                onDismiss = viewModel::dismissDialog,
                                //todo look here
                                onOkClick = {
                                    viewModel.dismissDialog()
                                    sendSMSMessagesPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                                },
                                onGoToAppSettingsClick = { requireActivity().openAppSettings() }
                            )
                        }

                    HomeScreen(
                        state = state,
                        eventHandler = {
                            viewModel.handleEvents(it)
                        },
                        onSendClick = { message ->
                            if (checkRequest()) {
                                val smsManager: SmsManager =
                                    requireContext().getSystemService(SmsManager::class.java)
                                state.numbers.forEach { number ->
                                    Log.d(
                                        "ahi3646",
                                        "onCreate: phoneNumber = $number ----- message = $message "
                                    )
                                    if (number.number.startsWith("+998")) {
                                        smsManager.sendTextMessage(
                                            number.number, null, message, null, null
                                        )
                                    } else {
                                        smsManager.sendTextMessage(
                                            "+998$number", null, message, null, null
                                        )
                                    }
                                }
                                //todo implement right toast
//                                Toast.makeText(
//                                    requireContext(), "SMS yuborildi",
//                                    Toast.LENGTH_LONG
//                                ).show()
                            } else {
                                sendSMSMessagesPermissionLauncher.launch(Manifest.permission.SEND_SMS)
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenNavigation.executeWithLifecycle(
            lifecycle = viewLifecycleOwner.lifecycle,
            action = ::executeNavigation
        )
    }

    private fun executeNavigation(navigation: HomeScreenNavigation) {
        when (navigation) {
            HomeScreenNavigation.OpenContacts -> {
                findNavController().navigate(R.id.action_homeFragment_to_contactsFragment)
            }

            HomeScreenNavigation.OpenCategories ->{
                findNavController().navigate(R.id.action_homeFragment_to_categoriesFragment)
            }

            is HomeScreenNavigation.AddCategory -> {
                val category = navigation.category
                val categories = preferenceHelper.getCategories()
                if (categories.list.isNotEmpty()) {
                    val isExist = categories.list.any {
                        it.name == category.name
                    }
                    if (isExist) {
                        Toast.makeText(
                            requireContext(),
                            "Group already exist with given name already exist!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val newList = categories.list.toMutableList().apply {
                            add(category)
                        }
                        val newCategories = Categories(newList)
                        preferenceHelper.saveCategories(newCategories)
                    }
                } else {
                    preferenceHelper.saveCategories(
                        Categories(
                            listOf(category)
                        )
                    )
                }
            }
        }
    }

}