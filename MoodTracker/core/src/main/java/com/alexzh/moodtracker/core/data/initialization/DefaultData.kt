package com.alexzh.moodtracker.core.data.initialization

import com.alexzh.moodtracker.core.domain.model.ActionCategory
import com.alexzh.moodtracker.core.domain.model.Action

object DefaultData {
    
    val defaultCategories = listOf(
        ActionCategory(
            id = 1,
            name = "Physical Activity"
        ),
        ActionCategory(
            id = 2,
            name = "Social"
        ),
        ActionCategory(
            id = 3,
            name = "Work"
        ),
        ActionCategory(
            id = 4,
            name = "Relaxation"
        ),
        ActionCategory(
            id = 5,
            name = "Hobbies"
        )
    )
    
    val defaultActions = listOf(
        // Physical Activity
        Action(id = 1, title = "Running", categoryId = 1),
        Action(id = 2, title = "Walking", categoryId = 1),
        Action(id = 3, title = "Gym Workout", categoryId = 1),

        // Social
        Action(id = 4, title = "Friends time", categoryId = 2),
        Action(id = 5, title = "Family time", categoryId = 2),
        Action(id = 6, title = "Party time", categoryId = 2),

        // Work
        Action(id = 7, title = "Planning", categoryId = 3),
        Action(id = 8, title = "Working", categoryId = 3),
        Action(id = 9, title = "Meeting", categoryId = 3),

        // Relaxation
        Action(id = 10, title = "Meditation", categoryId = 4),
        Action(id = 11, title = "Listening to music", categoryId = 4),
        Action(id = 12, title = "Watching movies", categoryId = 4),
        
        // Hobbies
        Action(id = 13, title = "Drawing", categoryId = 5),
        Action(id = 14, title = "Writing", categoryId = 5),
        Action(id = 15, title = "Cooking", categoryId = 5),
        Action(id = 16, title = "Reading", categoryId = 5),
        Action(id = 17, title = "Gaming", categoryId = 5)
    )
}