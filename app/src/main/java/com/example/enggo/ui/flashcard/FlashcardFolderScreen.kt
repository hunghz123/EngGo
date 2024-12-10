package com.example.enggo.ui.flashcard

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.enggo.R
import com.example.enggo.model.FlashcardFolder
import com.example.enggo.ui.theme.EngGoTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField
import com.google.firebase.firestore.toObject

private val fcCollectionRef = Firebase.firestore.collection("Flashcard")
private val folderCollectionRef = Firebase.firestore.collection("Folder")

@Composable
fun FlashcardHomeScreen(
    navController : NavController,
    inited : Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("EngGoApp", Context.MODE_PRIVATE)
    val idUser: String? = sharedPref.getString("currentUserId", null)
    var names = remember { mutableStateListOf<String>() }
    var nums = remember { mutableStateListOf<String>() }
    var ids = remember { mutableStateListOf<String>() }
    var filterText by remember { mutableStateOf("Enter Filter") }
    var init by remember {mutableStateOf(inited)}

    if (init == 1) {
        folderCollectionRef
            .whereEqualTo("userId", idUser)
            .get()
            .addOnSuccessListener { documents->
                for (document in documents) {
                    var id : String = document.id
                    ids.add(id)
                    nums.add("")
                    names.add("")
                }
                Log.d("Firebase", "get document success")
            }
        init = 0
    }


    for (index in 0..ids.size - 1) {
        var name: String = ""
        folderCollectionRef.document(ids[index])
            .get()
            .addOnSuccessListener { document ->
                name = document.get("name").toString()
                names[index] = name
            }
        var numOfItems: String = ""
        fcCollectionRef.whereEqualTo("folderid", ids[index])
            .get()
            .addOnSuccessListener { documents ->
                numOfItems = documents.count().toString()
                nums[index] = numOfItems
            }
    }



    Column(modifier = modifier) {
        FlashcardHomeTopbar(navController)

        TextField(
            value = filterText,
            onValueChange = { filterText = it },
            label = { Text("Filter") },
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, bottom = 40.dp, end = 10.dp)
        )

        if (nums.size >= ids.size) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(ids) { index, item ->
                    FlashcardView(
                        navController = navController,
                        name = names[index],
                        numOfItems = nums[index],
                        id = ids[index]
                    )
                }
            }
        }
    }



}

@Composable
fun FlashcardView(
    navController: NavController,
    name: String,
    numOfItems: String,
    id : String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 30.dp, end = 16.dp)
            .clickable {
                navController.navigate("FlashcardReview" + "/" + id)
                Log.d("Firebase", "Clicked " + id)
            }
    ) {
        Column {
            Text(
                text = name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 60.dp)
            )

            Text(
                text = numOfItems + " items",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
            )
        }
    }
}

@Composable
fun FlashcardHomeTopbar(
    navController: NavController
) {
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
                .clickable {
                navController.navigate("Flashcard_create")
            }
        ) {
            Image(
                painter = painterResource(R.drawable.plus_icon),
                contentDescription = "Add Flashcard Button",
                modifier = Modifier.size(50.dp)
                    .padding(end = 16.dp)
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun TopBarPreview() {
    EngGoTheme {

    }
}

