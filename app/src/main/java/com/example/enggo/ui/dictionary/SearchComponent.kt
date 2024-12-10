package com.example.enggo.ui.dictionary

import android.provider.SearchRecentSuggestions
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.enggo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun<T> SearchComponent(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
    onDoneActionClick: () -> Unit = {},
    onItemClick: (T) -> Unit = {},
    itemContent: @Composable (T) -> Unit = {},
    suggestions: List<T>,
) {
    Log.d("SearchComponent", "Suggestions received: $suggestions")

    var searchQuery by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var expandedState by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusState = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.padding_small)),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.text.isNotBlank())
                    onSearch(it.text)
                expandedState = suggestions.isNotEmpty()
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search for a word",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    modifier = Modifier.size(dimensionResource(R.dimen.small_icon_image_size)),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchQuery.text.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = TextFieldValue()
                        onClear()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.delete),
                            modifier = Modifier.size(dimensionResource(R.dimen.small_icon_image_size)),
                            contentDescription = null
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            keyboardActions = KeyboardActions(onDone = {
                onDoneActionClick()
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
//                .focusRequester(focusRequester)
//                .onFocusChanged { focusState.value = it.isFocused }
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()
            .padding(start = dimensionResource(R.dimen.padding_medium))
//            .heightIn(max = 300.dp)
        ) {
            items(suggestions.take(8)) { label ->
                Text(
                    text = label.toString(),
                    modifier = Modifier.clickable {
                        onItemClick(label)
                        searchQuery = TextFieldValue(
                            label.toString(),
                            selection = TextRange(label.toString().length)
                        )
                    }
                )
            }
        }
    }
}

//@Composable
//@Preview
//fun SearchComponentTest() {
//    SearchComponent()
//}