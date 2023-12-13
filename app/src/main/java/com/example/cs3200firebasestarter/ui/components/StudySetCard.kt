package com.example.cs3200firebasestarter.ui.components

import android.widget.Button
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cs3200firebasestarter.ui.animation.CircularLoading
import com.example.cs3200firebasestarter.ui.models.StudySetModel
import com.example.cs3200firebasestarter.ui.theme.button
import com.example.cs3200firebasestarter.ui.theme.cardColor
import com.example.cs3200firebasestarter.ui.theme.deleteRed
import com.example.cs3200firebasestarter.ui.viewmodels.AddStudySetViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudySetCard(
    studySet: StudySetModel,
    userName : String,
    accuracy : Int?,
    navHostController: NavHostController,
    id : String,
    onClick: () -> Unit,
){
    val viewModel: AddStudySetViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    LazyRow(
        modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp),
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
    ){
        item{
            Surface(
                modifier = Modifier
                    .width(400.dp)
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .clickable {
                        onClick()
                    }
                    .padding(10.dp),
                shadowElevation = 8.dp,
                color = Color(46, 56, 86),
                shape = RoundedCornerShape(8.dp)
            ){
                Row {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(2F)
                    ) {
                        studySet.studySetName?.let {
                            Text(
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(246, 247, 251),
                                text = it
                            )
                        }
                        val size = studySet.studySet?.size?.toInt()
                        Text(
                            color = Color(246, 247, 251),
                            text = "$size term${if (size != null && size > 1) "s" else ""}"
                        )

                        Spacer(modifier = Modifier.height(50.dp))
                        Row{
                            CircleAvatar(userName = userName)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxSize()
                            .padding(10.dp),
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 0.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                modifier = Modifier.clickable {
                                    navHostController.navigate("buildCharacter?id=${id}")
                                },
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(23.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 3.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            if (accuracy != null && accuracy > 0) {
                                val angle = (accuracy / 100f) * 360
                                CircularLoading(
                                    startAngle = 0f,
                                    sweepAngle = angle,
                                    text = "$accuracy %",
                                    fontSize = 20.sp,
                                    radius = 80.dp,
                                    strokeWidth = 7.dp
                                )
                            }else if(accuracy == 0){
                                CircularLoading(
                                    startAngle = 0f,
                                    sweepAngle = 5f,
                                    text = "$accuracy %",
                                    fontSize = 20.sp,
                                    radius = 80.dp,
                                    strokeWidth = 7.dp
                                )
                            }
                        }
                    }
                }
            }
        }
        
        item{
            Surface(
                modifier = Modifier
                    .width(140.dp)
                    .height(170.dp)
                    .clickable {
                        scope.launch {
                            viewModel.deleteStudySet()
                        }
                    }
                    .padding(10.dp),
                shadowElevation = 8.dp,
                color = deleteRed,
                shape = RoundedCornerShape(8.dp)
            ){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Icon(
                        modifier = Modifier
                            .size(65.dp).align(Alignment.Center),
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

fun colorFromName(userName: String) : Color {
    val orange = Color(255, 87, 34)
    val green = Color(76, 175, 80)
    val blue = Color(33, 150, 243)
    val yellow = Color(255, 193, 7)
    val pink = Color(233, 30, 99)
    val purple = Color(171, 71, 188)
    val firstLetterAscii = userName.take(1).uppercase().toCharArray()[0].toInt()

    return when (firstLetterAscii % 6) {
        0 -> orange
        1 -> green
        2 -> blue
        3 -> yellow
        4 -> pink
        else -> purple
    }
}


@Composable
fun CircleAvatar(userName: String) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(30.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            color = colorFromName(userName),
        ) {
            Text(
                text = userName.take(1).uppercase(),
                textAlign = TextAlign.Center,
                color = Color(246, 247, 251),
                fontWeight = FontWeight.Bold,
            )
        }
    }

    Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing between the circle and the text

    Text(
        color = Color(246, 247, 251),
        text = userName
    )
}