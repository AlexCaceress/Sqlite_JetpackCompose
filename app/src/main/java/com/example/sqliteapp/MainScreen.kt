package com.example.sqliteapp

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sqliteapp.room.BookEntity
import com.example.sqliteapp.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel,
    navController: NavController
) {
    var inputBook by remember { mutableStateOf("") }
    val empty by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val books by viewModel.books.collectAsState(initial = emptyList())

    val closeDialog = { showDialog = false }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Book name",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    ) {
        if (books.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Text("No name available")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(books) {
                    Card(
                        onClick = {
                            navController.navigate(
                                "Card/${Uri.encode(it.id.toString())}/${Uri.encode(it.title)}"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row {
                            Text(
                                text = "" + it.id,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(14.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "" + it.title,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(14.dp),
                            )
                        }
                    }
                }
            }
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = closeDialog,
            dismissButton = {
                Button(
                    onClick = closeDialog
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                if (inputBook.isNotEmpty()) {
                    Button(
                        onClick = {
                            viewModel.addBook(
                                BookEntity(
                                    id = 0,
                                    title = inputBook
                                )
                            )
                            showDialog = false
                            inputBook = empty
                        }
                    ) {
                        Text("Save")
                    }
                }
            },
            title = {
                Text(
                    text = "Add book name",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(5.dp)
                )
            },
            text = {
                OutlinedTextField(
                    value = inputBook,
                    onValueChange = {inputBook = it},
                    label = {
                        Text(text = "Book name")
                    },
                    placeholder = {
                        Text(text = "Enter your book name")
                    }
                )
            }
        )
    }

}