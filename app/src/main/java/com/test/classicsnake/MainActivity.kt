package com.test.classicsnake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.classicsnake.ui.theme.ClassicSnakeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ClassicSnakeViewModel = viewModel()
            ClassicSnakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                //MainScreen(viewModel,innerPadding)
                //Radar()
                    Box(Modifier.padding(innerPadding)){
                        Game2048()
                    }
                }
            }
        }
    }
}

