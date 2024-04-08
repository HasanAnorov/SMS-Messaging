package com.ierusalem.smsmessage.contacts.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.contacts.data.ContactItemModel
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    contact: ContactItemModel,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val drawableId = if(isSelected) R.drawable.check else R.drawable.user
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground)
                .size(32.dp),
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
                text = contact.contactName,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp, top = 4.dp),
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun ContactItemPreviewLight() {
    SMSMessageTheme {
        ContactItem(
            contact = ContactItemModel(
                contactName = "Hasan Anorov",
                phoneNumber = "+99893373646"
            ),
            isSelected = false,
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun ContactItemPreviewDark() {
    SMSMessageTheme {
        ContactItem(
            contact = ContactItemModel(
                contactName = "Hasan Anorov",
                phoneNumber = "+99893373646"
            ),
            isSelected = false,
            onItemClick = {}
        )
    }
}