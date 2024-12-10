package com.example.enggo.ui.profile

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.enggo.R
import com.example.enggo.model.ProfileData
import com.example.enggo.ui.theme.EngGoTheme

@Composable
internal fun ProfileRoute(
    onLogoutClick: () -> Unit,
    onClickProfile: () -> Unit,
    onClickAccount: () -> Unit
) {
    ProfileScreen(onLogoutClick, onClickProfile, onClickAccount)
}

@Composable
fun ProfileScreen(onLogoutClick: () -> Unit, onClickProfile: () -> Unit, onClickAccount: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
        val avatarResource = remember { mutableStateOf(R.drawable.default_avatar) } //TODO avatar default

        val coroutineScope = rememberCoroutineScope()
        val sharedPref = context.getSharedPreferences("EngGoApp", Context.MODE_PRIVATE)
        val idUser: String? = sharedPref.getString("currentUserId", null)
        var userData by remember { mutableStateOf<ProfileData?>(null) }

        LaunchedEffect(idUser) {
            if (idUser != null) {
                userData = getCurrentProfileData(idUser, coroutineScope)
            }
        }
        if (userData?.avatar != "null") {
            Image(
                painter = rememberAsyncImagePainter(userData?.avatar),
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.default_avatar), // Ảnh mặc định
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userData?.displayName.toString(),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 0.dp, 16.dp, 0.dp)
                .clickable {
                    onClickProfile()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Edit Profile Icon"
            )
            Text("Edit Profile")
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowRight, // TODO: check
                contentDescription = "Arrow Right"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp, 0.dp, 16.dp, 0.dp)
                .clickable(onClick = onClickAccount),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Account Management Icon"
            )
            Text("Account Management")
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowRight, // TODO: check
                contentDescription = "Arrow Right"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onLogoutClick()
                logout(context) },
        ) {
            Text("Logout")
        }
    }

}

fun logout(context: Context) {
    val sharedPref = context.getSharedPreferences("EngGoApp", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("currentUserId", null)
        putString("currentUserName", null)
        apply()
    }
}

@Composable
fun AboutMeCard(userData : ProfileData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "About Me", style = MaterialTheme.typography.titleMedium)
            // Replace with actual About Me text
            Text(text = userData.bio.toString(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    EngGoTheme(darkTheme = false) {
        Surface() {
            ProfileScreen({}, {}, {})
        }
    }
}

@Preview
@Composable
fun AboutMeCardPreview() {
    EngGoTheme(darkTheme = false) {
        Surface() {
            AboutMeCard(
                ProfileData(
                    displayName = "John Doe",
                    avatar = "https://www.example.com/avatar.jpg",
                    bio = "This is a sample bio",
                    id = "1",
                )
            )
        }
    }
}

