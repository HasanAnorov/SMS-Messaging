package com.ierusalem.smsmessage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.no_data_available)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
fun EmptyScreen_LightPreview() {
    SMSMessageTheme(darkTheme = false) {
        EmptyScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@Preview
@Composable
fun EmptyScreen_DarkPreview() {
    SMSMessageTheme(darkTheme = true) {
        EmptyScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}