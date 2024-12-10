package com.example.enggo.ui.flashcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enggo.R
import com.example.enggo.model.Flashcard
import com.example.enggo.model.FlashcardFolder
import com.example.enggo.ui.theme.EngGoTheme

@Composable
fun editFCFolderScreen(fcFolder : FlashcardFolder) {
    var init by remember  { mutableStateOf(0) }
    var FolderName by remember { mutableStateOf(fcFolder.name) }

    var term = remember { mutableStateListOf<Flashcard>() }
    if (init == 0) {
        for (x in fcFolder.flashcardList)
            term.add(x)
        init = 1
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Top bar",
            fontSize = 20.sp,
            modifier = Modifier.height(50.dp)
        )

        TextField(
            value = FolderName,
            onValueChange = { new_foldername ->
                FolderName = new_foldername
            },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
        )

        Text(
            text = "Title",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )

        Row() {
            Card(
                modifier = Modifier.fillMaxWidth(0.5f)
                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    .border(1.dp, Color.Black)
                    .clickable {
                        term.add(Flashcard("", ""))
                    }
            ) {
                Text(
                    text = "Add Term",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .border(1.dp, Color.Black)
                    .clickable {
                        //Done
                    }
            ) {
                Text(
                    text = "Finish",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            itemsIndexed(term) { index, item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                ) {
                    Column() {
                        Row(modifier = Modifier.height(30.dp)) {
                            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f))
                            if (term.size >= 3) {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                        .clickable {
                                            term.removeAt(index)
                                        }
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.deletebutton),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                        TextField(
                            value = term[index].FirstCard,
                            onValueChange = { term[index].FirstCard = it },
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                        )

                        Text(
                            text = "TERM",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        
                        TextField(
                            value = term[index].SecondCard,
                            onValueChange = { term[index].SecondCard = it },
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                        )

                        Text(
                            text = "DEFINITION",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun editFCFolderPreview() {
    EngGoTheme {
        var t : FlashcardFolder = FlashcardFolder("ABC")
        t.addFlashcard(Flashcard("abc", "xyz"))
        t.addFlashcard(Flashcard("abc2", "xyz2"))
        t.addFlashcard(Flashcard("abc3", "xyz3"))
        Surface(modifier = Modifier.fillMaxSize()) {
            editFCFolderScreen(fcFolder = t)
        }
    }
}