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
import ru.akaardent.primitivejetpackcomposecalculator.ui.theme.MyApplicationTheme

val buttonSize = 35.sp

class MainActivity : ComponentActivity() {

    private val mainScreen = "screen1"
    private val investmentsScreen = "screen2"
    private val notesScreen = "screen3"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                NavHost(
                    navController = navController,
                    startDestination = mainScreen
                ) {
                    composable(mainScreen) {
                        MainScreen(
                            clickToInvestmentsScreen = { navController.navigate(investmentsScreen) },
                            clickToNotesScreen = { navController.navigate(notesScreen) },
                        )
                    }
                    composable(investmentsScreen) {
                        InvestmentsScreen(
                            clickToMainScreen = {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.navigate(mainScreen)
                            },
                            clickToNotesScreen = {
                                navController.popBackStack()
                                navController.navigate(notesScreen)
                            },
                        )
                    }
                    composable(notesScreen) {
                        NotesScreen(
                            clickToMainScreen = {
                                navController.popBackStack()
                                navController.popBackStack()
                                navController.navigate(mainScreen)
                            },
                            clickToInvestmentsScreen = {
                                navController.popBackStack()
                                navController.navigate(investmentsScreen)
                            },
                        )
                    }


                }
            }

        }
    }
}
