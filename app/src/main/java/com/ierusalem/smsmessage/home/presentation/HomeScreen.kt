package com.ierusalem.smsmessage.home.presentation

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.ierusalem.smsmessage.MainScreenState
import com.ierusalem.smsmessage.PhoneNumber
import com.ierusalem.smsmessage.R
import com.ierusalem.smsmessage.ui.components.NumberItem
import com.ierusalem.smsmessage.ui.theme.SMSMessageTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: MainScreenState,
    eventHandler: (HomeScreenEvents) -> Unit,
    onSendClick: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    var phoneNumber by remember { mutableStateOf("") }
    var message by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 9
    val context = LocalContext.current

    ProvideWindowInsets {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            contentWindowInsets = ScaffoldDefaults
                .contentWindowInsets
                .exclude(WindowInsets.navigationBars)
                .exclude(WindowInsets.ime),
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = padding)
                        .verticalScroll(scrollState)
                        .imePadding()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 32.dp, top = 16.dp),
                        text = stringResource(R.string.your_message),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        minLines = 6,
                        maxLines = 8,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.titleSmall,
                        value = message,
                        onValueChange = {
                            message = it
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_your_message),
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                    )
                    Text(
                        modifier = Modifier.padding(start = 32.dp, top = 8.dp),
                        text = stringResource(R.string.phone_number),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        minLines = 1,
                        maxLines = 1,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.titleSmall,
                        value = phoneNumber,
                        onValueChange = {
                            if (it.length <= maxChar) phoneNumber = it
                        },
                        leadingIcon = {
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = stringResource(id = R.string.country_code),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        content = {
                            IconButton(
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 6.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .fillMaxHeight()
                                    .align(alignment = Alignment.CenterVertically)
                                    .background(color = MaterialTheme.colorScheme.primary),
                                onClick = {
                                    val phoneNumberOld =
                                        PhoneNumber(name = "Added", number = phoneNumber)
                                    when {
                                        !state.numbers.contains(phoneNumberOld) && phoneNumber.isNotEmpty() -> {
                                            eventHandler(HomeScreenEvents.OnAddClick(phoneNumberOld))
                                            phoneNumber = ""
//                                            onAddNumberClick(phoneNumberOld)
//                                            Toast.makeText(context, context.getString(R.string.number_added), Toast.LENGTH_SHORT).show()
                                        }

                                        phoneNumber.isEmpty() -> {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.enter_number),
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }

                                        state.numbers.contains(phoneNumberOld) -> {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.number_exist),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                },
                                content = {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "add phone number",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            )
                            IconButton(
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 6.dp, start = 8.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .fillMaxHeight()
                                    .align(alignment = Alignment.CenterVertically)
                                    .background(color = MaterialTheme.colorScheme.primary),
                                onClick = { eventHandler(HomeScreenEvents.OnContactsClick) },
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "add phone number",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            )
                        }
                    )
                    if (state.numbers.isNotEmpty()) {
                        val lazyState = rememberLazyListState()
                        LaunchedEffect(state.numbers.size) {
                            lazyState.animateScrollToItem(index = 0)
                        }
                        LazyColumn(
                            modifier = Modifier
                                .weight(1F)
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            state = lazyState,
                            content = {
                                items(items = state.numbers.reversed(), key = { it.number }) { number ->
                                    NumberItem(
                                        modifier = Modifier.animateItemPlacement(
                                            animationSpec = tween(
                                                durationMillis = 600
                                            )
                                        ),
                                        phoneNumber = number,
                                        onDeleteClick = {
                                            eventHandler(HomeScreenEvents.OnDeleteClick(number))
                                        }
                                    )
                                }
                            }
                        )
                    }
                    ButtonWithElevation(
                        modifier = Modifier
                            .navigationBarsWithImePadding()
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ),
                        onSendClick = {
                            if (message.text.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.enter_your_message),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                onSendClick(message.text)
                            }
                        }
                    )
                }
            }
        )
    }
}


@Composable
fun ButtonWithElevation(
    modifier: Modifier = Modifier,
    onSendClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onSendClick,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(size = 12.dp),
        content = {
            Image(
                painter = painterResource(id = R.drawable.baseline_send_24),
                contentDescription = "Send button icon",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(vertical = 6.dp),
                text = stringResource(R.string.send_message),
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall
            )
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    SMSMessageTheme(darkTheme = false) {
        HomeScreen(
            state = MainScreenState(),
            eventHandler = {},
            onSendClick = {},
        )
    }
}

@Preview
@Composable
fun MainScreenPreviewDark() {
    SMSMessageTheme(darkTheme = true) {
        HomeScreen(
            state = MainScreenState(),
            eventHandler = {},
            onSendClick = {},
        )
    }
}
