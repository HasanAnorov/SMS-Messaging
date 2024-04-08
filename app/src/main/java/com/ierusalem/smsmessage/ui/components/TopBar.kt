package com.ierusalem.smsmessage.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    containerColor: Color = MaterialTheme.colorScheme.background,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    navigationIcon: ImageVector = Icons.Filled.ArrowBack,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleColor,
        ),
        title = title,
        navigationIcon = {
            IconButton(onClick = { onNavIconPressed() }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = null
                )
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommonTopBarPreview() {
    SMSMessageTheme {
        CommonTopBar(
            title = {
                Text(
                    text = "Contacts",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommonTopBarPreviewDark() {
    SMSMessageTheme(darkTheme = true) {
        CommonTopBar(
            title = {
                Text(
                    text = "Contacts",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        )
    }
}