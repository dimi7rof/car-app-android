package com.example.carapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapp.data.FuelEntryEntity
import com.example.carapp.ui.components.AddFuelDialog

@Composable
fun FuelScreen(fuelEntries: List<FuelEntryEntity>, onAddFuel: (String, String, String, String) -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        AddFuelDialog(onDismiss = { showDialog = false }, onSave = { liters, price, odometer, date ->
            onAddFuel(liters, price, odometer, date)
            showDialog = false
        })
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Fuel", style = MaterialTheme.typography.h5)
            Button(onClick = { showDialog = true }) {
                Text(text = "Add")
            }
        }

        if (fuelEntries.isEmpty()) {
            Text(text = "No fuel records yet. Add one to get started.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(fuelEntries) { entry ->
                    FuelRow(entry)
                }
            }
        }
    }
}

@Composable
private fun FuelRow(entry: FuelEntryEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${entry.liters} L at $${"%.2f".format(entry.pricePerLiter)}", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Odometer: ${entry.odometer}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Date: ${entry.date}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Cost: $${"%.2f".format(entry.totalCost)}", style = MaterialTheme.typography.body2)
        }
    }
}
