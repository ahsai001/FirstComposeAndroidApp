package com.ahsailabs.firstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahsailabs.firstcomposeapp.ui.theme.FirstComposeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppScaffold()
                }
            }
        }
    }
}

@Composable
fun AppScaffold() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryAsState()
    var showOverFlowMenu by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentScreen?.destination?.route.toString())
                },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open()} }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open()} }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                    }
                    IconButton(onClick = { showOverFlowMenu = !showOverFlowMenu }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = showOverFlowMenu,
                        onDismissRequest = { showOverFlowMenu = false },
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {
                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "")
                            Text(text = "Refresh", modifier = Modifier.padding(start = 10.dp))
                        }
                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                            Text(text = "Call", modifier = Modifier.padding(start = 10.dp))
                        }
                    }
                }
            )
        },
        content = {
            NavHost(navController = navController, startDestination = "Home Page") {
                composable("Home Page"){
                    Home(navController)
                }
                composable("Profile Page"){
                    Profile()
                }
                composable("About Page"){
                    About()
                }
                composable("Info Page"){
                    Info()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
             FloatingActionButton(onClick = { /*TODO*/ }) {
                 Icon(imageVector = Icons.Filled.Add, contentDescription = "desc", modifier = Modifier.padding(all = 10.dp))
             }
        },
        drawerContent = {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Home",
                modifier = Modifier
                    .padding(all = 10.dp)
                    .clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            navController.navigate("Home Page")
                        }
                    })
            Text(
                text = "Profile",
                modifier = Modifier
                    .padding(all = 10.dp)
                    .clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            navController.navigate("Profile Page")
                        }
                    })
            Text(
                text = "About",
                modifier = Modifier
                    .padding(all = 10.dp)
                    .clickable {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                            navController.navigate("About Page")
                        }
                    })
        }
    )
}

@Composable
fun Home(navController: NavController){
    Center {
        Text(text = "Hello this is home")
        Button(onClick = { /*TODO*/ }) {
            Text("Go to Info Page", modifier = Modifier.clickable {
                navController.navigate("Info Page")
            })
        }
    }

}

@Composable
fun Profile(){
    Center {
        Text(text = "Hello this is profile")
    }
}

@Composable
fun About(){
    var count by rememberSaveable { mutableStateOf(0)}
    Center{
        Text(text = "Hello this is about")
        Text(text = "Your number is $count")
        Button(onClick = { count++ }) {
            Text("increment")
        }
        Button(onClick = { count-- }) {
            Text("increment")
        }
    }

}

@Composable
fun Info(){
    Center {
        Text(text = "Hello this is info")
    }

}



//
@Composable
fun Center(content: @Composable () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstComposeAppTheme {
        AppScaffold()
    }
}