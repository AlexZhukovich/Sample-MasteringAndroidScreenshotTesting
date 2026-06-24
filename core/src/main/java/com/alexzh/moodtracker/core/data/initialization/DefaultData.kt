package com.alexzh.moodtracker.core.data.initialization

import com.alexzh.moodtracker.core.domain.model.ActionCategory
import com.alexzh.moodtracker.core.domain.model.Action

object DefaultData {
    const val CATEGORY_PHYSICAL_ACTIVITY_LABEL = "category_physicalActivity_label"
    const val CATEGORY_SOCIAL_ACTIVITY_LABEL = "category_socialActivity_label"
    const val CATEGORY_PRODUCTIVITY_LABEL = "category_productivity_label"
    const val CATEGORY_RELAXATION_LABEL = "category_relaxation_label"
    const val CATEGORY_HOBBIES_LABEL = "category_hobbies_label"
    const val CATEGORY_ROUTINE_LABEL = "category_routine_label"

    const val ACTION_RUNNING_LABEL = "action_running_label"
    const val ACTION_WALKING_LABEL = "action_walking_label"
    const val ACTION_EXERCISING_LABEL = "action_exercising_label"
    const val ACTION_FAMILY_TIME_LABEL = "action_familyTime_label"
    const val ACTION_FRIENDS_TIME_LABEL = "action_friendsTime_label"
    const val ACTION_PARTY_TIME_LABEL = "action_partyTime_label"
    const val ACTION_EATING_OUT_LABEL = "action_eatingOut_label"
    const val ACTION_SOCIAL_MEDIA_LABEL = "action_socialMedia_label"
    const val ACTION_LEARNING_LABEL = "action_learning_label"
    const val ACTION_WORKING_LABEL = "action_working_label"
    const val ACTION_MEETING_LABEL = "action_meeting_label"
    const val ACTION_PERSONAL_PROJECT_LABEL = "action_personalProject_label"
    const val ACTION_MEDITATING_LABEL = "action_meditating_label"
    const val ACTION_LISTENING_TO_MUSIC_LABEL = "action_listeningToMusic_label"
    const val ACTION_WATCHING_MOVIES_LABEL = "action_watchingMovies_label"
    const val ACTION_REST_LABEL = "action_rest_label"
    const val ACTION_DRAWING_LABEL = "action_drawing_label"
    const val ACTION_WRITING_LABEL = "action_writing_label"
    const val ACTION_COOKING_LABEL = "action_cooking_label"
    const val ACTION_READING_LABEL = "action_reading_label"
    const val ACTION_GAMING_LABEL = "action_gaming_label"
    const val ACTION_MORNING_ROUTINE_LABEL = "action_morningRoutine_label"
    const val ACTION_EVENING_ROUTINE_LABEL = "action_eveningRoutine_label"
    const val ACTION_PLANNING_LABEL = "action_planning_label"
    const val ACTION_COMMUTING_LABEL = "action_commuting_label"
    const val ACTION_SLEEP_LABEL = "action_sleep_label"
    
    val defaultCategories = listOf(
        ActionCategory(id = 1, name = CATEGORY_PHYSICAL_ACTIVITY_LABEL),
        ActionCategory(id = 2, name = CATEGORY_SOCIAL_ACTIVITY_LABEL),
        ActionCategory(id = 3, name = CATEGORY_PRODUCTIVITY_LABEL),
        ActionCategory(id = 4, name = CATEGORY_RELAXATION_LABEL),
        ActionCategory(id = 5, name = CATEGORY_HOBBIES_LABEL),
        ActionCategory(id = 6, name = CATEGORY_ROUTINE_LABEL)
    )
    
    val defaultActions = listOf(
        // Physical Activity
        Action(id = 1, title = ACTION_RUNNING_LABEL, categoryId = 1),
        Action(id = 2, title = ACTION_WALKING_LABEL, categoryId = 1),
        Action(id = 3, title = ACTION_EXERCISING_LABEL, categoryId = 1),

        // Social Activity
        Action(id = 4, title = ACTION_FAMILY_TIME_LABEL, categoryId = 2),
        Action(id = 5, title = ACTION_FRIENDS_TIME_LABEL, categoryId = 2),
        Action(id = 6, title = ACTION_PARTY_TIME_LABEL, categoryId = 2),
        Action(id = 7, title = ACTION_EATING_OUT_LABEL, categoryId = 2),
        Action(id = 8, title = ACTION_SOCIAL_MEDIA_LABEL, categoryId = 2),

        // Productivity
        Action(id = 9, title = ACTION_LEARNING_LABEL, categoryId = 3),
        Action(id = 10, title = ACTION_WORKING_LABEL, categoryId = 3),
        Action(id = 11, title = ACTION_MEETING_LABEL, categoryId = 3),
        Action(id = 12, title = ACTION_PERSONAL_PROJECT_LABEL, categoryId = 3),

        // Relaxation
        Action(id = 13, title = ACTION_MEDITATING_LABEL, categoryId = 4),
        Action(id = 14, title = ACTION_LISTENING_TO_MUSIC_LABEL, categoryId = 4),
        Action(id = 15, title = ACTION_WATCHING_MOVIES_LABEL, categoryId = 4),
        Action(id = 16, title = ACTION_REST_LABEL, categoryId = 4),

        // Hobbies
        Action(id = 17, title = ACTION_DRAWING_LABEL, categoryId = 5),
        Action(id = 18, title = ACTION_WRITING_LABEL, categoryId = 5),
        Action(id = 19, title = ACTION_COOKING_LABEL, categoryId = 5),
        Action(id = 20, title = ACTION_READING_LABEL, categoryId = 5),
        Action(id = 21, title = ACTION_GAMING_LABEL, categoryId = 5),

        // Routine
        Action(id = 22, title = ACTION_MORNING_ROUTINE_LABEL, categoryId = 6),
        Action(id = 23, title = ACTION_EVENING_ROUTINE_LABEL, categoryId = 6),
        Action(id = 24, title = ACTION_PLANNING_LABEL, categoryId = 6),
        Action(id = 25, title = ACTION_COMMUTING_LABEL, categoryId = 6),
        Action(id = 26, title = ACTION_SLEEP_LABEL, categoryId = 6)
    )
}