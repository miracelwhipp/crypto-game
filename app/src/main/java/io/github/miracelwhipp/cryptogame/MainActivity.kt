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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.miracelwhipp.cryptogame.ui.theme.CryptoGameTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CryptoRiddleUI(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}


@Composable
fun CryptoRiddleUI(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var tappedChar by remember { mutableStateOf<Char?>(null) }

    var riddle by remember { mutableStateOf(CryptoRiddle.randomRiddle(context)) }
    var solution by remember { mutableStateOf(riddle.cypher.decrypt(riddle.text)) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(16.dp).verticalScroll(scrollState)) {
        Button(onClick = {
            riddle = CryptoRiddle.randomRiddle(context)
            solution = riddle.cypher.decrypt(riddle.text)
        }) {
            Text("Neues Rätsel")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Surface(color = Color.Cyan) {
            Text(
                text = riddle.text,
                onTextLayout = { textLayoutResult = it },
                style = TextStyle(
                    fontSize = 23.sp,
                    fontFamily = FontFamily.Monospace
                ),
                modifier = modifier.pointerInput(Unit) {
                    detectTapGestures { offset ->

                        val layout = textLayoutResult ?: return@detectTapGestures

                        val raw = layout.getOffsetForPosition(offset)
                        val charX = layout.getHorizontalPosition(raw, usePrimaryDirection = true)

                        val index =
                            if (raw > 0 && offset.x < charX) raw - 1 else raw

                        val tappedIndex = index.coerceIn(0, riddle.text.length - 1)

                        if (tappedIndex in riddle.text.indices) {

                            tappedChar = riddle.text[index]

                            if (tappedChar != null && riddle.canBeGuessed(tappedChar!!)) {
                                showDialog = true
                            }
                        }
                    }
                }
            )
        }
//                        Spacer(modifier = Modifier.height(12.dp))
//                        Surface(color = Color.Red) {
//                            Text(
//                                text = solution
//                            )
//                        }
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

                    if (riddle.isSolved()) {

                        Toast.makeText(
                            context,
                            "Geschafft! Nochmal?",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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