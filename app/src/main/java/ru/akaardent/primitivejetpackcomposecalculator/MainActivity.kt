package ru.akaardent.primitivejetpackcomposecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.akaardent.primitivejetpackcomposecalculator.screens.InvestmentsScreen
import ru.akaardent.primitivejetpackcomposecalculator.screens.MainScreen
import ru.akaardent.primitivejetpackcomposecalculator.screens.NotesScreen

val buttonSize = 35.sp

class MainActivity : ComponentActivity() {


    private val screen1 = "screen1"
    private val screen2 = "screen2"
    private val screen3 = "screen3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = screen2
            ) {
                composable(screen1) {
                    MainScreen(
                        {
                            navController.navigate(screen1)
                        },
                        {
                            navController.navigate(screen2)
                        },
                        {
                            navController.navigate(screen3)
                        },
                    )
                }
                composable(screen2) {
                    InvestmentsScreen(
                        {
                            navController.navigate(screen1)
                        },
                        {
                            navController.navigate(screen2)
                        },
                        {
                            navController.navigate(screen3)
                        },
                    )
                }
                composable(screen3) {
                    NotesScreen(
                        {
                            navController.navigate(screen1)
                        },
                        {
                            navController.navigate(screen2)
                        },
                        {
                            navController.navigate(screen3)
                        },
                    )
                }


            }
        }
    }
}
