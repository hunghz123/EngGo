package com.example.enggo.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.enggo.R
import com.example.enggo.data.service.UserService
import com.example.enggo.ui.theme.EngGoTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant

@Composable
fun LoginRoute(
    onLoginClick : () -> Unit,
    redirectToRegister: () -> Unit
) {
    LoginScreen(
        onLoginClick = onLoginClick,
        redirectToRegister
    )
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    redirectToRegister: () -> Unit
) {
    var userNameState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var loginError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.app_icon), //TODO: change logo later
            contentDescription = "App Logo",
            modifier = Modifier
                .size(156.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Text(
            text = "Log in to EngGo",
            style = MaterialTheme.typography.titleLarge,
            )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome back! Log in to your account to continue.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Login Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = userNameState,
                onValueChange = { userNameState = it },
                placeholder = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Username",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordState,
                onValueChange = {passwordState = it},
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    // display password if passwordVisible is true
                    VisualTransformation.None
                } else {
                    // hide password if passwordVisible is false
                    PasswordVisualTransformation()
                       },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Password,
                        contentDescription = "Password"
                    )
                              },
                trailingIcon = {
                    // display an icon to toggle password visibility
                    val icon = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                    Icon(
                        imageVector = icon,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.clickable {
                            passwordVisible = !passwordVisible
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val success = login(
                            userNameState.text,
                            passwordState.text,
                            context
                        )
                        if (success) {
                            onLoginClick()
                        } else {
                            loginError = true
                            kotlinx.coroutines.delay(1000L)
                            loginError = false
                        }
                    }
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (loginError) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Username or password is incorrect",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Don't have an account? Register",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        redirectToRegister()
                    }),
                textAlign = TextAlign.End
            )
        }
    }
}

suspend fun login(
    userName: String,
    password: String,
    context: Context
): Boolean {
    val userService = UserService(FirebaseFirestore.getInstance())
    val acceptLogin = userService.verifyLoginInfo(userName, password)
    val currentUserId = userService.getUserIdByUsername(userName)
    if (acceptLogin) {
        // Save currentUserId to SharedPreferences
        runBlocking {
            var token: String
            launch {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        token = task.getResult()
                        userService.addFCMToken(token, currentUserId!!)
                        Log.d("FCM", "Current user token: $token")
                    }
                }
            }.join()
            val sharedPref = context.getSharedPreferences("EngGoApp", Context.MODE_PRIVATE)
            val sessionTimeout = sharedPref.getString("session", "0")
            Log.d("SESSION", sessionTimeout.toString())
            with(sharedPref.edit()) {
                putString("currentUserId", currentUserId)
                putString("currentUsername", userName)
                if (sessionTimeout == "0" || sessionTimeout == null) {
                    putString("session", Instant.now().toEpochMilli().toString())
                }
                apply()
            }
        }
    }
    return acceptLogin
}

@Preview
@Composable
fun LoginScreenPreview() {
    EngGoTheme {
        Surface() {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    EngGoTheme(darkTheme = true) {
        Surface() {
            LoginScreen(onLoginClick = {}, redirectToRegister = {})
        }
    }
}
