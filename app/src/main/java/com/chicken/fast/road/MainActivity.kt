package com.chicken.fast.road

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chicken.fast.road.controller.Container
import com.chicken.fast.road.controller.LocalAppScore
import com.chicken.fast.road.navigation.Navigation
import com.chicken.fast.road.navigation.Route
import com.chicken.fast.road.ui.base.CircleButton
import com.chicken.fast.road.ui.theme.ChickenFastRoadTheme
import com.chicken.fast.road.ui.theme.gradientBrush
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/")

            ChickenFastRoadTheme {
                Container {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(getBgByRoute(currentRoute)),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                if (currentRoute != Route.LOADING.route) {
                                    TopBar(
                                        currentRoute = currentRoute,
                                        navController = navController
                                    )
                                }
                                Navigation(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    currentRoute: String?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val score = LocalAppScore.current
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        if (currentRoute != Route.HOME.route) {
            CircleButton(
                image = R.drawable.img_circle_btn_home,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                if (currentRoute != Route.HOME.route) {
                    navController.navigate(Route.HOME.route)
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(10.dp))
                .background(gradientBrush)
                .align(Alignment.Center)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.img_coin),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$score",
                    color = Color.White,
                    fontWeight = FontWeight.W900,
                    fontSize = 16.sp
                )
            }
        }

        if (currentRoute != Route.SETTINGS.route) {
            CircleButton(
                image = R.drawable.img_circle_btn_settings,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                if (currentRoute != Route.SETTINGS.route) {
                    navController.navigate(Route.SETTINGS.route)
                }
            }
        }
    }
}

private fun getBgByRoute(route: String?): Int {
    return if (route == Route.GAME.route) R.drawable.img_bg_2 else R.drawable.img_bg
}