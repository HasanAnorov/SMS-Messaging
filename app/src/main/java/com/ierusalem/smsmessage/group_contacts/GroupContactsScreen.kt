package com.ierusalem.smsmessage.group_contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.smsmessage.PhoneNumber
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.components.CommonTopBar
import com.ierusalem.smsmessage.ui.components.EmptyScreen
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupContactsScreen(
    groupName: String,
    contacts: List<PhoneNumber>,
    onNavClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                onNavIconPressed = onNavClick,
                title = {
                    Text(
                        text = "$groupName - ${stringResource(R.string.group_contacts)}",
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
        if (contacts.isEmpty()) {
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
                        items(contacts.size) { index ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .padding(vertical = 8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onBackground)
                                        .size(36.dp),
                                    painter = painterResource(id = R.drawable.user),
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
                                        text = contacts[index].name,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(bottom = 8.dp, top = 4.dp),
                                        text = contacts[index].number,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onBackground.copy(0.8F)
                                    )
                                }
                            }
                            if (index != contacts.size - 1) {
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
            }
        }
    }
}

@Preview
@Composable
private fun PreviewContactsScreenLight() {
    SMSMessageTheme {
        GroupContactsScreen(
            onNavClick = {},
            groupName = "Hasan",
            contacts = listOf(
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
            )
        )
    }
}

@Preview
@Composable
private fun PreviewContactsScreenDark() {
    SMSMessageTheme(darkTheme = true) {
        GroupContactsScreen(
            onNavClick = {},
            groupName = "Husna",
            contacts = listOf(
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
                PhoneNumber(
                    name = "Hasan",
                    number = "+99893373646"
                ),
            )
        )
    }
}