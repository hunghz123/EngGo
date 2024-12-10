package com.example.enggo.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enggo.data.service.UserService
import com.example.enggo.model.UserData
import com.example.enggo.ui.theme.EngGoTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@Composable
internal fun RegisterRoute(
    onRegisterClick: () -> Unit,
    redirectToLogin: () -> Unit
) {
    RegisterScreen(onRegisterClick, redirectToLogin)
}


@Composable
fun RegisterScreen(onRegisterClick: () -> Unit, redirectToLogin: () -> Unit) {
    var userState by remember { mutableStateOf(TextFieldValue()) }
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var checkPasswordState by remember { mutableStateOf(TextFieldValue()) }
    val userService = UserService(FirebaseFirestore.getInstance())
    val coroutineScope = rememberCoroutineScope()
    var isUsernameAvailable by remember { mutableStateOf(true) }
    var isEmailAvailable by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }
    var checkPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign up with Email",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 26.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Start learning with EngGo",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = userState,
                onValueChange = {userState = it
                    coroutineScope.launch {
                        isUsernameAvailable = userService.checkUsernameAvailability(it.text)
                    }},
                placeholder = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true
            )
            if (isUsernameAvailable && userState.text != "") {
                Text(
                    text = "Username is available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            OutlinedTextField(
                value = emailState,
                onValueChange = {emailState = it
                    coroutineScope.launch {
                        isEmailAvailable = userService.checkEmailAvailability(it.text)
                    }
                },
                placeholder = { Text("Your Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true
            )
            if(isEmailAvailable && emailState.text != "") {
                Text(text = "Email is not available",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            OutlinedTextField(
                value = passwordState,
                onValueChange = { passwordState = it},
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                visualTransformation = if (passwordVisible) {
                    // display password if passwordVisible is true
                    VisualTransformation.None
                } else {
                    // hide password if passwordVisible is false
                    PasswordVisualTransformation()
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
            OutlinedTextField(
                value = checkPasswordState,
                onValueChange = { checkPasswordState = it},
                placeholder = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                visualTransformation = if (checkPasswordVisible) {
                    // display password if passwordVisible is true
                    VisualTransformation.None
                } else {
                    // hide password if passwordVisible is false
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    // display an icon to toggle password visibility
                    val icon = if (checkPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                    Icon(
                        imageVector = icon,
                        contentDescription = if (checkPasswordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.clickable {
                            checkPasswordVisible = !checkPasswordVisible
                        }
                    )
                }
            )
            if(checkPasswordState != passwordState && checkPasswordState.text != "") {
                Text(text = "Password is incorrect!",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            val onRegister:()->Unit = {
                val userdata = UserData(
                    userState.text,
                    passwordState.text,
                    emailState.text,
                    null,
                )
                println(userdata.id)
                coroutineScope.launch {
                    if(userService.addUserData(userdata) != null) {
                        onRegisterClick()
                        println("User added")
                    }
                }
            }
            Button(
                onClick = onRegister, //handle register
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
            ) {
                Text(text = "Create an account", color = Color.White, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Already have an account? Login",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable(onClick = {
                        redirectToLogin()
                    }),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview
@Composable
fun RegisterScreenPreview() {
    EngGoTheme {
        Surface() {
            RegisterScreen({}, {})
        }

    }
}

@Preview
@Composable
fun RegisterScreenDarkPreview() {
    EngGoTheme(darkTheme = true) {
        Surface() {
            RegisterScreen({}, {})
        }
    }
}
