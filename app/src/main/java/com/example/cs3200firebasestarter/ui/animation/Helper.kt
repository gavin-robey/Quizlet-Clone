package com.example.cs3200firebasestarter.ui.animation

import com.example.cs3200firebasestarter.ui.models.CardData

class Helper{
    // Changes a cards flip state once the rotation reaches 90 degrees
    fun clamp(value: Float, min: Float = -90f, max: Float = 90f, isFlipped: Boolean): Pair<Float, Boolean> {
        var newValue = value
        var newIsFlipped = isFlipped

        if (value >= max) {
            newIsFlipped = false
            newValue = -89f
        } else if (value <= min) {
            newIsFlipped = true
            newValue = 89f
        }
        return Pair(newValue, newIsFlipped)
    }

    // Returns the previous element in the list of cards only if it exists
    fun getPreviousElement(list: List<CardData>, currentIndex: Int): CardData? {
        if (currentIndex > 0) {
            return list[currentIndex - 1]
        }
        return null
    }

    // Returns the next element in the list of cards only if it exists
    fun getNextElement(list: List<CardData>, currentIndex: Int): CardData? {
        if (currentIndex < list.size - 1) {
            return list[currentIndex + 1]
        }
        return null
    }

}