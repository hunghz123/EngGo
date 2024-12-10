package com.example.enggo.ui.dictionary

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.enggo.R
import com.example.enggo.model.dictionary.Meaning
import com.example.enggo.model.dictionary.WordModel


@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel,
    onItemClick: (Int) -> Unit
) {
    val bookmarks by viewModel.bookmarks.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Bookmarks",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp)
                        .align(Alignment.Start)
                )

                BookmarkList(list = bookmarks, onItemClick = onItemClick) {
//                    viewModel.deleteBookmark(it)
                    Log.d("xoa bookmark", "da nhan icon xoa bookmark")
                }
            }
            if (bookmarks.isEmpty()) {
                Text(
                    text = "Bookmark is empty",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .align(Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun BookmarkList(
    list: List<WordModel>,
    onItemClick: (Int) -> Unit,
    onDeleteClick: (WordModel) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))) {
        itemsIndexed(list) { index, item ->
            BookmarkItem(index, wordModel = item, onItemClick, onDeleteClick)
        }
    }
}

@Composable
fun BookmarkItem(
    index: Int,
    wordModel: WordModel,
    onItemClick: (Int) -> Unit,
    onDeleteClick: (WordModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .wrapContentHeight()
            .clickable {
                onItemClick(index)
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(4f)
            ) {
                Text(
                    text = wordModel.word,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "1. ${wordModel.meanings?.get(0)?.def}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                )
                wordModel.meanings?.get(0)?.synonyms?.let {
                    Text(
                        text = "Synonym(s): ${
                            it.toString().removePrefix("[")
                                .removeSuffix("]")
                        }",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                    )
                }
                wordModel.meanings?.get(0)?.example?.let {
                    Text(
                        text = "Ex: $it",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

            IconButton(
                onClick = { onDeleteClick(wordModel) },
                modifier = Modifier.weight(.5f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun BookmarkScreenPreview() {
//    BookmarkScreen(
//        viewModel = BookmarkViewModel()
//    )
//}