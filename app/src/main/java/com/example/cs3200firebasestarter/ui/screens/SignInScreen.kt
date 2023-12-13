package com.example.cs3200firebasestarter.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.repositories.UserRepository
import com.example.cs3200firebasestarter.ui.theme.background
import com.example.cs3200firebasestarter.ui.theme.button
import com.example.cs3200firebasestarter.ui.theme.secondaryButton
import com.example.cs3200firebasestarter.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .background(color = background)
            .padding(10.dp)
    ){
        TextButton(
            onClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background)
                .padding(25.dp, 25.dp, 25.dp, 25.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(6F)
            ) {
                FormField(
                    value = state.email,
                    onValueChange = { state.email = it },
                    placeholder = { Text("Enter Your Email") },
                    error = state.emailError,
                    title = "",
                )
                FormField(
                    value = state.password,
                    onValueChange = { state.password = it },
                    placeholder = { Text("Enter Your Password") },
                    error = state.passwordError,
                    password = true,
                    title = "",
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.signIn()
                        if (UserRepository.getCurrentUserId() != null) {
                            navHostController.navigate(Routes.appNavigation.route) {
                                popUpTo(navHostController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = button
                ),
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "Log In"
                )
            }
        }
    }
}