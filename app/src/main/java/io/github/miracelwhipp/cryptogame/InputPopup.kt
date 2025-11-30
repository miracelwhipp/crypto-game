package io.github.miracelwhipp.cryptogame


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InputPopup(
    title: String = "Enter a character",
    exampleText: String = "Type 1 character",
    onConfirm: (Char) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = {
                        // Limit input to 1 character
                        if (it.text.length <= 1) text = it
                    },
                    singleLine = true,
                    placeholder = { Text(exampleText) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {

                if (text.text.isNotEmpty()) {

                    onConfirm(text.text[0])

                } else {

                    Toast.makeText(context, "Bitte gib einen Buchstaben ein.", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PopupPreview() {
    InputPopup(
        title = "substitute A",
        onDismiss = {},
        onConfirm = {character -> }
    )
}

