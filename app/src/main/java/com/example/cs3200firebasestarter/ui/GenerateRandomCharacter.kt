package com.example.cs3200firebasestarter.ui

import kotlin.random.Random

class GenerateRandomCharacter {
    val names = listOf("Larry Hummus", "Barry Dingle", "Cheese Schidts", "Steven Quaggleton",
        "Kevin Bricks", "Mr Tinkleton", "Frankie Doodlyboy", "Sir Small", "George", "King Whoarbeard")
    val races = listOf("Human", "Elf", "Dwarf", "Orc", "Gnome", "Halfling", "Weeble", "Hobbit")
    val classes = listOf("Warrior", "Wizard", "Rogue", "Cleric", "Bard", "Druid", "Jester")
    val genders = listOf("Male", "Female", "Other")
    val descriptions = listOf(
        "A brave adventurer on a quest.",
        "A mysterious wanderer with a dark past.",
        "A jolly bard who loves to sing and tell stories.",
        "A mischievous rogue skilled in thievery and stealth.",
        "A wise wizard with powerful magical abilities.",
        "A lover of all fine cheeses",
        "Seeking a lover but has absolutely no game whatsoever"
    )

    fun <T> getRandomElement(list: List<T>): T {
        return list[Random.nextInt(0, list.size)]
    }
}
