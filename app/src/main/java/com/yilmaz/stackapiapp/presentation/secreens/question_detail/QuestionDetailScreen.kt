package com.yilmaz.stackapiapp.presentation.secreens.question_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.yilmaz.stackapiapp.domain.model.questions.Question

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsDetailScreen(navController: NavController, questionModel: Question?) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Question Details",
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        shareQuestion(context, questionModel?.link ?: "")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Question"
                        )
                    }
                })
        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {
            val url = questionModel?.link ?: "https://stackoverflow.com/"
            var backEnabled by remember { mutableStateOf(false) }
            var webView: WebView? = null
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                backEnabled = view.canGoBack()
                            }
                        }
                        settings.javaScriptEnabled = true
                        loadUrl(url)
                        webView = this
                    }
                }, update = {
                    webView = it
                })
            BackHandler(enabled = backEnabled) {
                webView?.goBack()
            }
        }
    }
}

private fun shareQuestion(context: Context, url: String) {
    Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }.also { sendIntent ->
        context.startActivity(Intent.createChooser(sendIntent, "Share question"))
    }
}

object QuestionDetail {
    const val QUESTION_DETAIL_ARGUMENT_KEY = "question_model"
}