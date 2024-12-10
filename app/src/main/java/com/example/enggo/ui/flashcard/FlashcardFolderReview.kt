package com.example.enggo.ui.flashcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enggo.R
import com.example.enggo.model.Flashcard
import com.example.enggo.model.FlashcardFolder
import com.example.enggo.ui.theme.EngGoTheme

@Composable
fun FlashcardFolderView(fcFolder : FlashcardFolder, modifier : Modifier = Modifier) {
    var pagePreview by remember { mutableStateOf(0) }
    var ok by remember { mutableStateOf(1) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Should be top bar",
            fontSize = 20.sp,
            modifier = Modifier.height(50.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.1f)
                    .clickable {
                        if (pagePreview > 0) {
                            pagePreview--
                            ok = 1
                        }
                    }
            ) {
                if (pagePreview > 0)
                    Image(
                        painter = painterResource(R.drawable.left_direction),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
            }
            Box(
                modifier = Modifier.fillMaxWidth(0.88f)
            ) {
                if (ok == 1) {
                    ok = 0
                    fcFolder.flashcardList[pagePreview].flashCardView(modifier = Modifier.fillMaxSize())
                }
                else
                    fcFolder.flashcardList[pagePreview].flashCardView(modifier = Modifier.fillMaxSize())
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .clickable {
                        if (pagePreview < fcFolder.flashcardNumber - 1) {
                            pagePreview++
                            ok = 1
                        }
                    }
            ) {
                if (pagePreview < fcFolder.flashcardNumber - 1)
                    Image(
                        painter = painterResource(R.drawable.right_direction),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
            }
        }

        Text(
            text = "${pagePreview + 1} / ${fcFolder.flashcardNumber}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
        )

        Text(
            text = fcFolder.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        )

        functionCard("Image", "Flashcards")
        functionCard("Image", "Learn")
        functionCard("Image", "Test")

        Text(
            text = "Cards",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
        )

        LazyColumn(
            modifier = Modifier.padding(bottom = 40.dp)
        ) {
            items(fcFolder.flashcardList) { item ->
                item.TwoSideFlashCardView(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                )
            }
        }
    }
}

@Composable
fun functionCard(firstText : String, secondText: String) {
    Card(modifier = Modifier.height(80.dp).fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            Text(
                text = firstText,
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth(0.25f).padding(start = 10.dp)
            )

            Text(
                text = secondText,
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlashcardFolderPreview() {
    EngGoTheme {
        var t : FlashcardFolder = FlashcardFolder()
        t.name = "Testing flashcard folder"
        t.addFlashcard(Flashcard("Vehicle", "Thuộc về xe cộ (adj)"))
        t.addFlashcard(Flashcard("vinicity", "Vùng lân cận, phụ cận"))
        t.addFlashcard(Flashcard("cater", "Phục vụ, cung cấp (v)"))
        t.addFlashcard(Flashcard("Pastoral Care", "Mục vụ (trách nhiệm chăm sóc và tư vấn được cung cấp bởi các nhà lãnh đạo tôn giáo)"))
        Surface(modifier = Modifier.fillMaxSize()) {
            FlashcardFolderView(fcFolder = t)
        }
    }
}