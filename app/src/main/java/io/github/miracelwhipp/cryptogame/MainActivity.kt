package io.github.miracelwhipp.cryptogame

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.miracelwhipp.cryptogame.ui.theme.CryptoGameTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoGameTheme {

                var riddle by remember { mutableStateOf(CryptoRiddle.randomRiddle()) }
                var solution by remember { mutableStateOf(riddle.cypher.decrypt(riddle.text)) }


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        CryptoRiddleUI(
                            modifier = Modifier.padding(innerPadding),
                            riddle
                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//                        Surface(color = Color.Red) {
//                            Text(
//                                text = solution
//                            )
//                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = {
                            riddle = CryptoRiddle.randomRiddle()
                            solution = riddle.cypher.decrypt(riddle.text)
                        }) {
                            Text("Neues Rätsel")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CryptoRiddleUI(
    modifier: Modifier = Modifier,
    riddle: CryptoRiddle = CryptoRiddle.randomRiddle(),
) {
    val context = LocalContext.current
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var tappedChar by remember { mutableStateOf<Char?>(null) }

    Surface(color = Color.Cyan) {
        Text(
            text = riddle.text,
            onTextLayout = { textLayoutResult = it },
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures { offset ->

                    val index = textLayoutResult?.getOffsetForPosition(offset)

                    index?.let {

                        val realIndex = it - 1

                        if (realIndex in riddle.text.indices) {

                            tappedChar = riddle.text[realIndex]

                            if (tappedChar != null && riddle.canBeGuessed(tappedChar!!)) {
                                showDialog = true
                            }
                        }
                    }
                }
            }
        )
    }

    if (showDialog && tappedChar != null) {

        InputPopup(
            title = "Was ist verschlüsselt '$tappedChar'?",
            exampleText = "$tappedChar",
            onConfirm = { inputCharacter ->

                if (riddle.guessCharacter(tappedChar!!, inputCharacter)) {

                    Toast.makeText(
                        context,
                        "Richtig",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        context,
                        "Leider nein",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    CryptoGameTheme {
        CryptoRiddleUI()
    }
}