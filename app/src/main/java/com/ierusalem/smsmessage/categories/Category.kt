package com.ierusalem.smsmessage.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme


@Composable
fun Category(
    name: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.inversePrimary.copy(0.5F))
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = name,
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
            )
//            todo add later
//            IconButton(
//                modifier = Modifier
//                    .clip(shape = RoundedCornerShape(12.dp))
//                    .align(alignment = Alignment.CenterVertically),
//                onClick = onDeleteClick,
//                content = {
//                    Icon(
//                        painter = painterResource(id = R.drawable.trash),
//                        contentDescription = "add phone number",
//                        tint = Color(0xFFFC4C53)
//                    )
//                }
//            )
        }
    )
}

@Preview
@Composable
fun NumberItemPreview() {
    SMSMessageTheme(darkTheme = false) {
        Category(name = "Husan", onDeleteClick = { /*TODO*/ })
    }
}

@Preview
@Composable
fun NumberItemPreviewDark() {
    SMSMessageTheme(darkTheme = true) {
        Category(name = "Hasan", onDeleteClick = { /*TODO*/ })
    }
}
