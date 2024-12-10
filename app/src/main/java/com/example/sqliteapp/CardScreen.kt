package com.example.sqliteapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sqliteapp.room.BookEntity
import com.example.sqliteapp.viewmodel.BookViewModel

@Composable
fun CardScreen(
    modifier: Modifier = Modifier,
    viewModel : BookViewModel,
    navController: NavController,
    bookId : String?,
    bookName : String?
) {
    var showDialog by remember { mutableStateOf(false) }
    var updateDialog by remember { mutableStateOf(false) }
    var inputBook by remember { mutableStateOf("") }
    val empty by remember { mutableStateOf("") }

    val closeDialog = { showDialog = false }
    val closeUpdateDialog = { updateDialog = false }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(15.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Row {
                    Text(
                        text = "Id: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = ""+bookId,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Row {
                    Text(
                        text = "Name: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = ""+bookName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {  }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    OutlinedIconButton(
                        onClick = {showDialog = true},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.size(height = 50.dp, width = 100.dp)
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.baseline_delete_24),
                                contentDescription = null
                            )
                            Text("Delete")
                        }
                    }
                    OutlinedIconButton(
                        onClick = {
                            updateDialog = true
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.size(height = 50.dp, width = 100.dp)
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.baseline_edit_24),
                                contentDescription = null
                            )
                            Text("Update")
                        }
                    }
                }
            }
        }
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = closeDialog,
            dismissButton = {
                Button(
                    onClick = closeDialog
                ) {
                    Text("NO")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(bookId != null){
                            viewModel.deleteBook(
                                book = BookEntity(
                                    id = bookId.toInt(),
                                    title = bookName.toString()
                                )
                            )
                            showDialog = false
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            title = {
                Text(
                    text = "Delete book",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )
            },
            text = {
                Text("Are your sure", fontSize = 20.sp)
            }
        )
    }

    if (updateDialog) {
        AlertDialog(
            onDismissRequest = closeUpdateDialog,
            dismissButton = {
                Button(
                    onClick = closeUpdateDialog
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                if (inputBook.isNotEmpty()) {
                    Button(
                        onClick = {
                            val newBook = BookEntity(
                                bookId!!.toInt(),
                                inputBook
                            )
                            viewModel.updateBook(newBook)
                            navController.popBackStack()
                            closeUpdateDialog()
                            inputBook = empty
                        }
                    ) {
                        Text("Update")
                    }
                }
            },
            title = {
                Text(
                    text = "Update book name",
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