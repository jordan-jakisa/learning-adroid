package com.empire.gemini_chat.ui.screens.chatScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.empire.gemini_chat.ui.theme.GeminichatTheme
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    vm: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var prompt by rememberSaveable { mutableStateOf("") }
    val uiState = vm.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    if (uiState.hasCompleted) {
        prompt = ""
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }, topBar = {
        MediumTopAppBar(title = { Text(text = "Gemini Chat") })
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                itemsIndexed(uiState.chats) { index, item ->
                    val isUser = index % 2 != 0

                    if (!isUser) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(16.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.primary)
                                    .fillMaxWidth(.85f)

                            ) {
                                Text(
                                    text = item,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(16.dp)
                                )

                            }
                        }


                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(16.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .fillMaxWidth(1f)

                            ) {
                                MarkdownText(
                                    markdown = item,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(enabled = !uiState.isLoading, value = prompt, onValueChange = { value ->
                    prompt = value
                }, placeholder = {
                    Text(text = "Start typing here ... ")
                }, modifier = Modifier.weight(1f)
                )

                Column(modifier = Modifier.size(54.dp)) {
                    if (uiState.isLoading) {

                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(48.dp)
                                .padding(8.dp)
                        )
                    } else {
                        IconButton(onClick = {
                            if (prompt.isNotEmpty()) vm.sendMessage(prompt)
                            else Toast.makeText(
                                context, "Prompt cannot be null.", Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CSP() {
    GeminichatTheme {
        ChatScreen()
    }
}