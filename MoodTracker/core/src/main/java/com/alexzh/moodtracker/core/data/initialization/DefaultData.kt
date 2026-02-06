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
            name = "Social Activity"
        ),
        ActionCategory(
            id = 3,
            name = "Productivity"
        ),
        ActionCategory(
            id = 4,
            name = "Relaxation"
        ),
        ActionCategory(
            id = 5,
            name = "Hobbies"
        ),
        ActionCategory(
            id = 6,
            name = "Routine"
        )
    )
    
    val defaultActions = listOf(
        // Physical Activity
        Action(id = 1, title = "Running", categoryId = 1),
        Action(id = 2, title = "Walking", categoryId = 1),
        Action(id = 3, title = "Exercising", categoryId = 1),

        // Social Activity
        Action(id = 4, title = "Friends time", categoryId = 2),
        Action(id = 5, title = "Family time", categoryId = 2),
        Action(id = 6, title = "Party time", categoryId = 2),
        Action(id = 7, title = "Eating out", categoryId = 2),
        Action(id = 8, title = "Social media", categoryId = 2),

        // Productivity
        Action(id = 9, title = "Learning", categoryId = 3),
        Action(id = 10, title = "Working", categoryId = 3),
        Action(id = 11, title = "Meeting", categoryId = 3),
        Action(id = 12, title = "Personal project", categoryId = 3),

        // Relaxation
        Action(id = 13, title = "Meditating", categoryId = 4),
        Action(id = 14, title = "Listening to music", categoryId = 4),
        Action(id = 15, title = "Watching movies", categoryId = 4),
        Action(id = 16, title = "Rest", categoryId = 4),

        // Hobbies
        Action(id = 17, title = "Drawing", categoryId = 5),
        Action(id = 18, title = "Writing", categoryId = 5),
        Action(id = 19, title = "Cooking", categoryId = 5),
        Action(id = 20, title = "Reading", categoryId = 5),
        Action(id = 21, title = "Gaming", categoryId = 5),

        // Routine
        Action(id = 22, title = "Morning routine", categoryId = 6),
        Action(id = 23, title = "Evening routine", categoryId = 6),
        Action(id = 24, title = "Planning", categoryId = 6),
        Action(id = 25, title = "Commuting", categoryId = 6),
        Action(id = 26, title = "Sleep", categoryId = 6)
    )
}