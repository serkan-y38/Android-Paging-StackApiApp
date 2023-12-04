package com.yilmaz.stackapiapp.presentation.secreens.questions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.yilmaz.stackapiapp.R
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.presentation.navigation.NavGraph
import com.yilmaz.stackapiapp.presentation.secreens.question_detail.QuestionDetail

@Composable
fun QuestionListItem(it: Question, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = QuestionDetail.QUESTION_DETAIL_ARGUMENT_KEY,
                    value = it
                )
                navController.navigate(route = NavGraph.QuestionDetailScreen.route)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Card(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp), shape = CircleShape
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clickable {
                                openLink(
                                    link = it.owner.ownerLink,
                                    context = context
                                )
                            },
                        painter = rememberAsyncImagePainter(
                            model = it.owner.ownerProfileImage,
                            error = painterResource(id = R.drawable.baseline_person_24),
                            placeholder = painterResource(id = R.drawable.baseline_person_24)
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Text(
                    text = it.owner.ownerDisplayName,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        .clickable {
                            openLink(
                                link = it.owner.ownerLink,
                                context = context
                            )
                        },
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = it.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp, 0.dp),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LazyRow {
                    items(it.tags) { tag ->
                        TagsListItem(tag)
                    }
                }
            }
        }
    }
}

@Composable
fun TagsListItem(tag: String) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(0.dp, 0.dp, 10.dp, 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .wrapContentWidth()
                .padding(10.dp, 5.dp, 10.dp, 5.dp)
        )
    }
}

fun openLink(link: String, context: Context) {
    Intent(
        Intent.ACTION_VIEW,
        Uri.parse(link)
    ).also {
        ContextCompat.startActivity(context, it, null)
    }
}