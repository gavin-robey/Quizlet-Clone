package com.example.cs3200firebasestarter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit,
    password: Boolean = false,
    error: Boolean = false,
    title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            colors = TextFieldDefaults
                .textFieldColors(
                    containerColor = Color.Transparent,
                    placeholderColor = Color(117, 124, 146),
                    textColor = Color(246, 247, 251),
                    focusedIndicatorColor = Color(255, 205, 31),
                    unfocusedIndicatorColor = Color(246, 247, 251),
                ),
            placeholder = placeholder,
            visualTransformation =
            if (password) PasswordVisualTransformation() else VisualTransformation.None,
            isError = error
        )

        Spacer(modifier = Modifier
            .height(1.dp)
            .background(Color.Gray))
        Spacer(modifier = Modifier.height(4.dp))
        Text(title ,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(217, 221, 232),
        )
    }
}