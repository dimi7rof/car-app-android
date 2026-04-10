package com.example.carapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.carapp.data.CarDatabase
import com.example.carapp.data.CarRepository
import com.example.carapp.ui.CarAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = CarDatabase.getDatabase(this)
        val repository = CarRepository(database)
        val viewModelFactory = MainViewModel.Factory(repository)
        val viewModel: MainViewModel by viewModels { viewModelFactory }

        setContent {
            CarAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
