package com.yilmaz.stackapiapp.presentation.navigation

sealed class NavGraph(val route: String) {
    object QuestionsScreen : NavGraph(route = "questions_screen")
    object QuestionDetailScreen : NavGraph(route = "question_detail_screen")
    object SettingsScreen : NavGraph(route = "settings_screen")
}
