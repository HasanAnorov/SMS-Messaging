package com.ierusalem.smsmessage.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ierusalem.smsmessage.R

@Composable
fun CategoryNameDialog(
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {

    val context = LocalContext.current
    var categoryName by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Enter name for a group!",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                minLines = 1,
                maxLines = 1,
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.titleSmall,
                value = categoryName,
                onValueChange = {
                    categoryName = it
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.group_name),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
            )
            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.primary),
                onClick = {
                    if (categoryName.length >3){
                        onSubmit(categoryName)
                        onDismiss()
                    }else{
                        Toast.makeText(context,
                            context.getString(R.string.group_name_shoud_be_more_than_3_characters), Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(stringResource(id = R.string.submit))
            }
        }
    }
}

@Preview
@Composable
private fun CategoryNamePreviewLight() {
    CategoryNameDialog(onSubmit = {}, onDismiss = {} )
}