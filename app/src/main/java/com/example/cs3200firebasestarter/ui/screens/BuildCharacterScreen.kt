package com.example.cs3200firebasestarter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.components.CardItem
import com.example.cs3200firebasestarter.ui.components.FormField
import com.example.cs3200firebasestarter.ui.models.CardData
import com.example.cs3200firebasestarter.ui.navigation.Routes
import com.example.cs3200firebasestarter.ui.theme.background
import com.example.cs3200firebasestarter.ui.theme.button
import com.example.cs3200firebasestarter.ui.theme.cardColor
import com.example.cs3200firebasestarter.ui.theme.deleteRed
import com.example.cs3200firebasestarter.ui.viewmodels.AddStudySetViewModel
import kotlinx.coroutines.launch

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

    // loads in data if we are editing or not
    LaunchedEffect(true) {
        viewModel.setUpInitialState(id)
    }

    Column {
        Row(modifier = Modifier
            .background(color = background)
            .padding(10.dp)){
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if(state.isInEditMode) "Edit" else "Create"} Study Set",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.End

            ){
                if(state.isInEditMode){
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.deleteStudySet()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .padding(vertical = 0.dp, horizontal = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = deleteRed
                        ),
                    ) {
                        Text(text = "Delete Set")
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))

                // Add set to the data base depending on the mode
                Button(
                    onClick = {
                        scope.launch {
                            if(state.isInEditMode){
                                viewModel.updateStudySet(true)
                            }else{
                                state.studySet.add(CardData(state.term, state.definition, false))
                                state.notFinished = false
                                viewModel.addStudySet()
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 0.dp, horizontal = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = button
                    ),
                ) {
                    Text(text = if(state.isInEditMode) "Done" else "Create")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = background)
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
            }

            for (index in state.studySet.indices) {
                CardItem(
                    card = state.studySet[index],
                    onCardChange = { newCard ->
                        state.studySet[index] = newCard
                    },
                    size = state.studySet.size,
                    delete = {
                        if(state.studySet.size > 1){
                            state.studySet.removeAt(index)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (state.notFinished && !state.isInEditMode) {
                Surface(
                    color = cardColor,
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
                        Spacer(modifier = Modifier.height(15.dp))
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
                    containerColor = cardColor
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