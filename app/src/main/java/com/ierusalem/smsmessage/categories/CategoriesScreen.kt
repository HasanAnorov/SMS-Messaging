package com.ierusalem.smsmessage.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.smsmessage.MainScreenState
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.components.CommonTopBar
import com.ierusalem.smsmessage.ui.components.EmptyScreen
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onNavClick: () -> Unit,
    uiState: MainScreenState
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                onNavIconPressed = onNavClick,
                title = {
                    Text(
                        text = stringResource(R.string.groups),
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
        val data = uiState.categories.list
        if (data.isEmpty()) {
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
                        items(data) {
                            Category(name = it.name, onDeleteClick = { /*TODO*/ })
                        }
                    }
                )

            }
        }
    }
}

@Preview
@Composable
private fun CategoriesScreenPreviewLight() {
    SMSMessageTheme {
        CategoriesScreen(
            onNavClick = {},
            uiState = MainScreenState()
        )
    }
}