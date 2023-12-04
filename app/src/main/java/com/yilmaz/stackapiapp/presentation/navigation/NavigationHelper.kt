package com.yilmaz.stackapiapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.presentation.secreens.question_detail.QuestionDetail.QUESTION_DETAIL_ARGUMENT_KEY
import com.yilmaz.stackapiapp.presentation.secreens.question_detail.QuestionsDetailScreen
import com.yilmaz.stackapiapp.presentation.secreens.questions.QuestionsScreen
import com.yilmaz.stackapiapp.presentation.secreens.settings.SettingsScreen

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = NavGraph.QuestionsScreen.route) {
        composable(
            route = NavGraph.QuestionsScreen.route
        ) {
            QuestionsScreen(navHostController)
        }
        composable(
            route = NavGraph.QuestionDetailScreen.route
        ) {
            val questionModel =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Question>(
                    QUESTION_DETAIL_ARGUMENT_KEY
                )
            QuestionsDetailScreen(navHostController, questionModel)
        }
        composable(
            route = NavGraph.SettingsScreen.route
        ) {
            SettingsScreen(navHostController)
        }
    }
}