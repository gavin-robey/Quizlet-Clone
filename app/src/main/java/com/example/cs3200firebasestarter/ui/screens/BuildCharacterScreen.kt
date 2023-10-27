package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.viewmodels.BuildCharacterViewModel
import kotlinx.coroutines.launch

@Composable
fun BuildCharacterScreen(navHostController: NavHostController, id : String?){
    val viewModel: BuildCharacterViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.saveSuccess){
        if(state.saveSuccess){
            navHostController.navigate(Routes.appNavigation.route) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
    LaunchedEffect(true){
        viewModel.setUpInitialState(id)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround) {
        Surface(shadowElevation = 2.dp) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                if(id == "new" || id == null){
                    Text(text = "Build Character", style = MaterialTheme.typography.headlineSmall)
                }else{
                    Text(text = "Edit Character", style = MaterialTheme.typography.headlineSmall)
                }
                FormField(
                    value = state.name,
                    onValueChange = { state.name = it },
                    placeholder = { Text("Character Name") },
                    error = state.emptyFields
                )
                FormField(
                    value = state.age,
                    onValueChange = { state.age = it},
                    placeholder = { Text("Character Age") },
                    error = state.emptyFields
                )
                FormField(
                    value = state.race,
                    onValueChange = { state.race = it },
                    placeholder = { Text("Race (elf, dwarf, human etc..)") },
                    error = state.emptyFields
                )
                FormField(
                    value = state._class,
                    onValueChange = { state._class = it },
                    placeholder = { Text("Class (mage, rouge, warrior etc..)") },
                    error = state.emptyFields
                )
                FormField(
                    value = state.height,
                    onValueChange = { state.height = it },
                    placeholder = { Text("Height") },
                    error = state.emptyFields
                )
                FormField(
                    value = state.gender,
                    onValueChange = { state.gender = it },
                    placeholder = { Text("Gender") },
                    error = state.emptyFields
                )
                FormField(
                    value = state.description,
                    onValueChange = { state.description = it },
                    placeholder = { Text("Description") },
                    error = state.emptyFields
                )
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    if(id == "new" || id == null){
                        Button(
                            onClick = {
                                viewModel.createRandomCharacter()
                            }
                        ){
                            Text(text = "Random Character")
                        }
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.buildCharacter()
                            }
                        },
                        elevation = null
                    ) {
                        Text(text = "Submit")
                    }
                }
                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}