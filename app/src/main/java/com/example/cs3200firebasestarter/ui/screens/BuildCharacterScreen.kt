package com.example.cs3200firebasestarter.ui.screens

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
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

    LaunchedEffect(id){
        println(id)
    }


    // add later when editing elements
//    LaunchedEffect(true) {
//        viewModel.setUpInitialState(id)
//    }
    Column {
        Row(modifier = Modifier
            .background(color = Color(0, 9, 45))
            .padding(10.dp)){
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create Study Set",
                    fontWeight = FontWeight.Bold,
                    color = Color(246, 247, 251)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.End

            ){
                Button(
                    onClick = {
                        state.studySet.add(CardData(state.term, state.definition, false))
                        state.notFinished = false
                        scope.launch {
                            viewModel.addStudySet()
                        }
                        state.saveSuccess = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 0.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(66, 62, 216)
                    ),
                ) {
                    Text(text = "Create")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color(0, 9, 45))
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                FormField(
                    value = state.studySetName,
                    onValueChange = { state.studySetName = it },
                    title = "Title",
                    placeholder = { Text("Subject, chapter, unit") },
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (state.showDescription) {
                    FormField(
                        value = state.description,
                        onValueChange = { state.description = it },
                        title = "Description",
                        placeholder = { Text("Subject, chapter, unit") },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            for (index in state.studySet.indices) {
                CardItem(
                    card = state.studySet[index],
                    onCardChange = { newCard ->
                        state.studySet[index] = newCard
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (state.notFinished) {
                Surface(
                    color = Color(46, 56, 86),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        FormField(
                            value = state.term,
                            onValueChange = { state.term = it },
                            title = "Term",
                            placeholder = { Text("") },
                        )
                        FormField(
                            value = state.definition,
                            onValueChange = { state.definition = it },
                            title = "Definition",
                            placeholder = { Text("") },
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    state.studySet.add(CardData(state.term, state.definition, false))
                    state.term = ""
                    state.definition = ""
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(46, 56, 86)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 0.dp, horizontal = 0.dp)
            ) {
                Text(text = "Add term")
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun CardItem(card: CardData, onCardChange: (CardData) -> Unit) {
    Surface(
        color = Color(46, 56, 86),
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ){
            FormField(
                value = card.front ?: "",
                onValueChange = {
                    onCardChange(CardData(it, card.back, false))
                },
                title = "Term",
                placeholder = {  },
            )
            FormField(
                value = card.back ?: "",
                onValueChange = {
                    // Update the back property of the Card
                    onCardChange(CardData(card.front, it, false))
                },
                title = "Definition",
                placeholder = {  },
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}