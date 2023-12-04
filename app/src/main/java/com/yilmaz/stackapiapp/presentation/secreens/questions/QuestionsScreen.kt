package com.yilmaz.stackapiapp.presentation.secreens.questions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yilmaz.stackapiapp.domain.model.questions.Question
import com.yilmaz.stackapiapp.presentation.navigation.NavGraph
import com.yilmaz.stackapiapp.presentation.view_models.QuestionsViewModel
import com.yilmaz.stackapiapp.presentation.view_models.SearchQuestionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun QuestionsScreen(
    navController: NavController,
    questionsViewModel: QuestionsViewModel = hiltViewModel(),
    searchQuestionViewModel: SearchQuestionViewModel = hiltViewModel(),
) {
    val searchedQuestions = searchQuestionViewModel.searchedQuestions.collectAsLazyPagingItems()
    val questions = questionsViewModel.pager.collectAsLazyPagingItems()

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val hostState = remember { SnackbarHostState() }

    val searchBarPadding: Dp by animateDpAsState(if (active) 0.dp else 10.dp, label = "")
    val surface = MaterialTheme.colorScheme.surface
    val context = LocalContext.current
    val elevatedSurface = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
    val items = setNavItems()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Title", style = MaterialTheme.typography.headlineLarge)
                }
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            when (selectedItemIndex) {
                                0 -> {
                                    //TODO
                                }

                                1 -> {
                                    //TODO
                                }

                                2 -> {
                                    navController.navigate(NavGraph.SettingsScreen.route)
                                }
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = hostState) },
            topBar = {
                androidx.compose.material3.SearchBar(
                    query = query,
                    active = active,
                    onActiveChange = {
                        active = it
                        if (active)
                            systemUiController.setSystemBarsColor(elevatedSurface)
                        else
                            systemUiController.setSystemBarsColor(surface)
                    },
                    placeholder = {
                        Text(text = "Search")
                    },
                    onQueryChange = {
                        query = it
                        searchQuestionViewModel.updateSearchQuery(query = it)
                        searchQuestionViewModel.searchQuestion(query = query)
                    },
                    onSearch = {
                        query = it
                        searchQuestionViewModel.updateSearchQuery(query = it)
                        searchQuestionViewModel.searchQuestion(query = query)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .animateContentSize()
                        .padding(searchBarPadding, 0.dp),
                    leadingIcon = {
                        if (active)
                            IconButton(onClick = {
                                systemUiController.setSystemBarsColor(surface)
                                active = false
                                query = ""
                                searchQuestionViewModel.updateSearchQuery(query = query)
                                searchQuestionViewModel.searchQuestion(query = query)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        else
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                    },
                    trailingIcon = {
                        Row {
                            IconButton(
                                onClick = {
                                    if (!active) {
                                        active = true
                                        systemUiController.setSystemBarsColor(elevatedSurface)
                                    } else
                                        searchQuestionViewModel.updateSearchQuery(query = query)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    }
                ) {
                    if (isConnected(context = context))
                        QuestionList(searchedQuestions, navController, 10)
                }
                QuestionList(questions, navController, 80)
            }
        ) {
            if (!isConnected(context = context))
                scope.launch {
                    hostState.showSnackbar(
                        message = "No Internet Connection",
                        duration = SnackbarDuration.Long,
                        withDismissAction = true,
                    )
                }
        }
    }
}

@Composable
fun QuestionList(
    questions: LazyPagingItems<Question>,
    navController: NavController,
    topPadding: Int
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topPadding.dp),
        state = listState
    ) {
        items(questions.itemCount) {
            questions[it]?.let { it1 ->
                QuestionListItem(it = it1, navController = navController)
            }
        }
    }
}

fun setNavItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Questions",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
        ),
        NavigationItem(
            title = "Share App",
            selectedIcon = Icons.Filled.Share,
            unselectedIcon = Icons.Outlined.Share,
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Suppress("DEPRECATION")
fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nInfo = cm.activeNetworkInfo
    return nInfo != null && nInfo.isAvailable && nInfo.isConnected
}