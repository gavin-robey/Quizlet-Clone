package com.example.cs3200firebasestarter.ui.screens

import android.content.ClipData.Item
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.viewmodels.AddStudySetViewModel
import kotlinx.coroutines.launch

// building software with ai Data 5570
@Composable
fun BuildCharacterScreen(navHostController: NavHostController, id : String?) {
//    val viewModel: BuildCharacterViewModel = viewModel()
    val viewModel: AddStudySetViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState


    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            navHostController.navigate(Routes.appNavigation.route) {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    // add later when editing elements
//    LaunchedEffect(true) {
//        viewModel.setUpInitialState(id)
//    }

    Column {
        FormField(
            value = state.studySetName,
            onValueChange = { state.studySetName = it },
            placeholder = { Text("Study Set Name") },
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(state.studySet.size) { index ->
                CardItem(card = state.studySet[index], onCardChange = { newCard ->
                    state.studySet[index] = newCard
                })
            }
        }
        if(state.notFinished){
            FormField(
                value = state.term,
                onValueChange = { state.term = it },
                placeholder = { Text("Term") },
            )
            FormField(
                value = state.definition,
                onValueChange = { state.definition = it},
                placeholder = { Text("Definition") },
            )
        }

        Button(onClick = {
            state.studySet.add(CardData(state.term, state.definition, false))
            state.term = ""
            state.definition = ""
        }) {
            Text(text = "add term")
        }

        Button(onClick = {
            state.studySet.add(CardData(state.term, state.definition, false))
            state.notFinished = false
            scope.launch {
                viewModel.addStudySet()
            }
            state.saveSuccess = true
        }) {
            Text(text = "Create")
        }
    }










//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.SpaceAround) {
//        Surface(shadowElevation = 2.dp) {
//            Column(modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)) {
//                if(id == "new" || id == null){
//                    Text(text = "Build Character", style = MaterialTheme.typography.headlineSmall)
//                }else{
//                    Text(text = "Edit Character", style = MaterialTheme.typography.headlineSmall)
//                }
//                FormField(
//                    value = state.name,
//                    onValueChange = { state.name = it },
//                    placeholder = { Text("Character Name") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state.age,
//                    onValueChange = { state.age = it},
//                    placeholder = { Text("Character Age") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state.race,
//                    onValueChange = { state.race = it },
//                    placeholder = { Text("Race (elf, dwarf, human etc..)") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state._class,
//                    onValueChange = { state._class = it },
//                    placeholder = { Text("Class (mage, rouge, warrior etc..)") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state.height,
//                    onValueChange = { state.height = it },
//                    placeholder = { Text("Height") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state.gender,
//                    onValueChange = { state.gender = it },
//                    placeholder = { Text("Gender") },
//                    error = state.emptyFields
//                )
//                FormField(
//                    value = state.description,
//                    onValueChange = { state.description = it },
//                    placeholder = { Text("Description") },
//                    error = state.emptyFields
//                )
//                Row (
//                    horizontalArrangement = Arrangement.End,
//                    modifier = Modifier.fillMaxWidth()
//                ){
//                    TextButton(onClick = { navHostController.popBackStack() }) {
//                        Text(text = "Cancel")
//                    }
//                    if(id == "new" || id == null){
//                        Button(
//                            onClick = {
//                                viewModel.createRandomCharacter()
//                            }
//                        ){
//                            Text(text = "Random Character")
//                        }
//                    }
//                    Button(
//                        onClick = {
//                            scope.launch {
//                                viewModel.buildCharacter()
//                            }
//                        },
//                        elevation = null
//                    ) {
//                        Text(text = "Submit")
//                    }
//                }
//                Text(
//                    text = state.errorMessage,
//                    style = TextStyle(color = MaterialTheme.colorScheme.error),
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Right
//                )
//            }
//        }
//    }
}

@Composable
fun CardItem(card: CardData, onCardChange: (CardData) -> Unit) {
    FormField(
        value = card.front,
        onValueChange = {
            // Update the front property of the Card
            onCardChange(CardData(it, card.back, false))
        },
        placeholder = { Text("Term") },
    )
    FormField(
        value = card.back,
        onValueChange = {
            // Update the back property of the Card
            onCardChange(CardData(card.front, it, false))
        },
        placeholder = { Text("Definition") },
    )
}