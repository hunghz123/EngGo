package com.example.enggo.ui.flashcard

import android.util.ArrayMap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


private val fcCollectionRef = Firebase.firestore.collection("Flashcard")
private val folderCollectionRef = Firebase.firestore.collection("Folder")

@Composable
fun FlashcardFolderView(id : String, modifier : Modifier = Modifier) {

    var pagePreview by remember { mutableStateOf(0) }
    var ok by remember { mutableStateOf(1) }
    var init by remember { mutableStateOf(1) }
    var flashcardNumber by remember { mutableStateOf(0) }
    var firstCard = remember { mutableListOf<String>() }
    var secondCard = remember { mutableListOf<String>() }
    var name by remember { mutableStateOf("Folder Name") }

    if (init == 1) {
        fcCollectionRef.whereEqualTo("folderid", id)
            .get()
            .addOnSuccessListener { documents->
                flashcardNumber = documents.count()
                for (document in documents) {
                    firstCard.add(document.get("firstCard").toString())
                    secondCard.add(document.get("secondCard").toString())
                }
            }

        folderCollectionRef.document(id)
            .get()
            .addOnSuccessListener { document->
                name = document.get("name").toString()
            }

        init = 0
    }


    if (init == 0) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "$name",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, top = 20.dp, bottom = 10.dp)
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
                    if (firstCard.size > 0) {
                        if (ok == 1) {
                            ok = 0
                            flashCardView(
                                Flashcard(firstCard[pagePreview], secondCard[pagePreview]),
                                modifier = Modifier.fillMaxSize()
                            )
                        } else
                            flashCardView(
                                Flashcard(firstCard[pagePreview], secondCard[pagePreview]),
                                modifier = Modifier.fillMaxSize()
                            )
                    }


                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            if (pagePreview < flashcardNumber - 1) {
                                pagePreview++
                                ok = 1
                            }
                        }
                ) {
                    if (pagePreview < flashcardNumber - 1)
                        Image(
                            painter = painterResource(R.drawable.right_direction),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                }
            }

            Text(
                text = "${pagePreview + 1} / ${flashcardNumber}",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
            )

            Text(
                text = "Cards",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
            )
            if (firstCard.size > 0) {
                LazyColumn(
                    modifier = Modifier.padding(bottom = 40.dp)
                ) {
                    itemsIndexed(firstCard) { index, item ->
                        TwoSideFlashCardView(
                            Flashcard(firstCard[index], secondCard[index]),
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
                        )
                    }
                }
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
fun QuickPreview() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp)
    ) {

        Text(
            text = "Folder",
            fontSize = 36.sp,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.plus_icon),
                contentDescription = "Add Flashcard Button",
                modifier = Modifier.size(36.dp)
                    .padding(end = 16.dp)
            )
        }
    }
}