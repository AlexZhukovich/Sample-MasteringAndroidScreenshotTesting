package com.alexzh.moodtracker.common.ui.model

import android.content.Context
import com.alexzh.moodtracker.common.ui.R
import com.alexzh.moodtracker.core.data.initialization.DefaultData

class LocalizedActionNameProvider(
    private val context: Context
) {
    fun getLocalizedName(name: String): String {
        val resId = nameToStringRes(name)
        return if (resId != null) context.getString(resId) else name
    }

    private fun nameToStringRes(name: String): Int? = when (name) {
        DefaultData.ACTION_RUNNING_LABEL -> R.string.action_running_label
        DefaultData.ACTION_WALKING_LABEL -> R.string.action_walking_label
        DefaultData.ACTION_EXERCISING_LABEL -> R.string.action_exercising_label
        DefaultData.ACTION_FAMILY_TIME_LABEL -> R.string.action_familyTime_label
        DefaultData.ACTION_FRIENDS_TIME_LABEL -> R.string.action_friendsTime_label
        DefaultData.ACTION_PARTY_TIME_LABEL -> R.string.action_partyTime_label
        DefaultData.ACTION_EATING_OUT_LABEL -> R.string.action_eatingOut_label
        DefaultData.ACTION_SOCIAL_MEDIA_LABEL -> R.string.action_socialMedia_label
        DefaultData.ACTION_LEARNING_LABEL -> R.string.action_learning_label
        DefaultData.ACTION_WORKING_LABEL -> R.string.action_working_label
        DefaultData.ACTION_MEETING_LABEL -> R.string.action_meeting_label
        DefaultData.ACTION_PERSONAL_PROJECT_LABEL -> R.string.action_personalProject_label
        DefaultData.ACTION_MEDITATING_LABEL -> R.string.action_meditating_label
        DefaultData.ACTION_LISTENING_TO_MUSIC_LABEL -> R.string.action_listeningToMusic_label
        DefaultData.ACTION_WATCHING_MOVIES_LABEL -> R.string.action_watchingMovies_label
        DefaultData.ACTION_REST_LABEL -> R.string.action_rest_label
        DefaultData.ACTION_DRAWING_LABEL -> R.string.action_drawing_label
        DefaultData.ACTION_WRITING_LABEL -> R.string.action_writing_label
        DefaultData.ACTION_COOKING_LABEL -> R.string.action_cooking_label
        DefaultData.ACTION_READING_LABEL -> R.string.action_reading_label
        DefaultData.ACTION_GAMING_LABEL -> R.string.action_gaming_label
        DefaultData.ACTION_MORNING_ROUTINE_LABEL -> R.string.action_morningRoutine_label
        DefaultData.ACTION_EVENING_ROUTINE_LABEL -> R.string.action_eveningRoutine_label
        DefaultData.ACTION_PLANNING_LABEL -> R.string.action_planning_label
        DefaultData.ACTION_COMMUTING_LABEL -> R.string.action_commuting_label
        DefaultData.ACTION_SLEEP_LABEL -> R.string.action_sleep_label
        else -> null
    }
}