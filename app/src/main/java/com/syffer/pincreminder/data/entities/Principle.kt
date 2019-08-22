package com.syffer.pincreminder.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Principle(
    @NotNull
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String,
    var description: String = "",
    var source: String = ""
)

val DATAFIXTURES = listOf(
    Principle(title = "Follow the style guide"),
    Principle(title = "Use descriptive names"),
    Principle(title = "Comment, Document, Readme"),
    Principle(title = "Package by features, not by layers"),
    Principle(title = "Keep It Simple Stupid"),
    Principle(title = "You Ain't Gonna Need It"),
    Principle(title = "SOLID - Single responsibility", description = "one job, the first rule of classes is taht they should be very small, the second is that they should be even smaller"),
    Principle(title = "SOLID - Open for extension, close for modification"),
    Principle(title = "SOLID - Liskov substitution", description = "a  subclass must be substituable for its super class"),
    Principle(title = "SOLID - Interface segregation", description = "clients should not be forced to depend upon interfaces that they do not use"),
    Principle(title = "SOLID - Dependency inversion", description = "dependency should be on abstractions not conceptions, interface pattern"),
    Principle(title = "Simplicity is always better than functionnality"),
    Principle(title = "Beautiful is better than ugly"),
    Principle(title = "Fix each broken window as soon as it is discovered", description = "a broken window is a bad design, a wrong decision, or poor code"),
    Principle(title = "Nothing is Something"),
    Principle(title = "Designing state-first leads to terrible coupling"),
    Principle(title = "Tell, don't ask (object)"),
    Principle(title = "The sum should be simpler to work with than its parts"),
    Principle(title = "Be relunctant to add code to God Objects"),
    Principle(title = "A user interface is like a joke, if you have to explain it, it's not that good"),
    Principle(title = "Trust, but verify")
)