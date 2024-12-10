package com.example.enggo.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enggo.ui.theme.EngGoTheme

class Flashcard (var FirstCard : String, var SecondCard : String) {

    @Composable
    fun flashCardView(modifier : Modifier = Modifier) {

        var s by remember { mutableStateOf(FirstCard) }

        Card(
            onClick = {
                if (s.equals(FirstCard)) {
                    s = SecondCard
                }
                else {
                    s = FirstCard
                }
            },
            modifier = modifier
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = s,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp)
                )
            }
        }
    }

    @Composable
    fun TwoSideFlashCardView(modifier : Modifier = Modifier) {
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = FirstCard,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                )

                Text(
                    text = SecondCard,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun FlashCardPreview() {
    EngGoTheme {
        var x : Flashcard = Flashcard("Duck", "Vit")
        x.flashCardView(modifier = Modifier.height(200.dp).width(400.dp))
    }
}

@Preview (showBackground = true)
@Composable
fun TwoSideFlashcardPreview() {
    EngGoTheme {
        var x : Flashcard = Flashcard("Duck", "Vit")
        x.TwoSideFlashCardView(modifier = Modifier.fillMaxWidth())
    }
}