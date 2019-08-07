package com.syffer.pincreminder.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Principle(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val description: String = "",
    val source: String = ""
)


val DATAFIXTURES = listOf(
    Principle(title = "Follow the style guide"),
    Principle(title = "Use descriptive names"),
    Principle(title = "Comment, Document, Readme"),
    Principle(title = "Package by features, not by layers"),
    Principle(title = "Keep It Simple Stupid"),
    Principle(title = "You Ain't Gonna Need It"),
    Principle(title = "Fix each broken window as soon as it is discovered"),
    Principle(title = "Nothing is Something"),
    Principle(title = "A user interface is like a joke, if you have to explain it, it's not that good")
)