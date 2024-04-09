package com.ierusalem.smsmessage.contacts.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.smsmessage.MainScreenState
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.components.CommonTopBar
import com.ierusalem.smsmessage.ui.components.EmptyScreen
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    uiState: MainScreenState,
    onNavClick: () -> Unit,
    onSubmitClick: (List<ContactItemModel>) -> Unit
) {
    var items by rememberSaveable {
        mutableStateOf(
            uiState.contacts
        )
    }
    LaunchedEffect(key1 = uiState.contacts) {
        items = uiState.contacts
    }
    Scaffold(
        topBar = {
            CommonTopBar(
                onNavIconPressed = onNavClick,
                title = {
                    Text(
                        text = stringResource(R.string.contacts),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
    ) { paddingValues ->
        if (uiState.contacts.isEmpty()) {
            EmptyScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            )
        } else {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .weight(1F)
                        .padding(bottom = 16.dp)
                        .padding(paddingValues),
                    content = {
                        items(items.size) { index ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        items = items.mapIndexed { i, item ->
                                            if (index == i) {
                                                item.copy(
                                                    isSelected = !item.isSelected
                                                )
                                            } else item
                                        }
                                    }
                                    .background(MaterialTheme.colorScheme.background),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val drawableId =
                                    if (items[index].isSelected) R.drawable.check else R.drawable.user
                                Image(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .padding(vertical = 8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onBackground)
                                        .size(36.dp),
                                    painter = painterResource(id = drawableId),
                                    contentDescription = null
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = items[index].contactName,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(bottom = 8.dp, top = 4.dp),
                                        text = items[index].phoneNumber,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.8F)
                                    )
                                }
                            }
                            if (index != uiState.contacts.size - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(
                                        start = 68.dp,
                                        end = 16.dp
                                    )
                                )
                            }
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .clickable {
                                val selectedItems = items.filter { it.isSelected }
                                onSubmitClick(selectedItems)
                            },
                        contentAlignment = Alignment.Center,
                        content = {
                            Text(
                                text = stringResource(R.string.submit),
                                modifier = Modifier
                                    .padding(vertical = 16.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                        },
                    )
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .clickable {
                                onSubmitClick(items)
                            },
                        contentAlignment = Alignment.Center,
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.submit_all),
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewContactsScreenLight() {
    SMSMessageTheme {
        ContactsScreen(
            onNavClick = {},
            onSubmitClick = {},
            uiState = MainScreenState(
                contacts = listOf(
                    ContactItemModel(
                        contactName = "Hasan",
                        phoneNumber = "+99893373646"
                    ),
                    ContactItemModel(
                        contactName = "Hasan",
                        phoneNumber = "+99893373646"
                    ),
                    ContactItemModel(
                        contactName = "Hasan",
                        phoneNumber = "+99893373646"
                    ),
                    ContactItemModel(
                        contactName = "Hasan",
                        phoneNumber = "+99893373646"
                    ),
                )
            )
        )
    }
}

@Preview
@Composable
private fun PreviewContactsScreenDark() {
    SMSMessageTheme(darkTheme = true) {
        ContactsScreen(
            onNavClick = {},
            onSubmitClick = {},
            uiState = MainScreenState(
                contacts = listOf(
//                    ContactItemModel(
//                        contactName = "Hasan",
//                        phoneNumber = "+99893373646"
//                    ),
//                    ContactItemModel(
//                        contactName = "Hasan",
//                        phoneNumber = "+99893373646"
//                    ),
//                    ContactItemModel(
//                        contactName = "Hasan",
//                        phoneNumber = "+99893373646"
//                    ),
//                    ContactItemModel(
//                        contactName = "Hasan",
//                        phoneNumber = "+99893373646"
//                    ),
                )
            )
        )
    }
}