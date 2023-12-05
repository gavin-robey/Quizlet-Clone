package com.example.cs3200firebasestarter.ui.models

data class CardData(
    var front: String? = null,
    var back: String? = null,
    var isFlipped : Boolean? = null,
    var isCorrect : Boolean? = null
)