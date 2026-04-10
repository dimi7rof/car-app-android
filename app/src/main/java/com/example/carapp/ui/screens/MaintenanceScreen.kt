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
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carapp.data.MaintenanceEntity
import com.example.carapp.ui.components.AddMaintenanceDialog

@Composable
fun MaintenanceScreen(records: List<MaintenanceEntity>, onAddMaintenance: (String, String, String, String) -> Unit) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        AddMaintenanceDialog(onDismiss = { showDialog = false }, onSave = { title, details, cost, date ->
            onAddMaintenance(title, details, cost, date)
            showDialog = false
        })
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Maintenance", style = MaterialTheme.typography.h5)
            Button(onClick = { showDialog = true }) {
                Text(text = "Add")
            }
        }

        if (records.isEmpty()) {
            Text(text = "No maintenance records yet. Add one to get started.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(records) { record ->
                    MaintenanceRow(record)
                }
            }
        }
    }
}

@Composable
private fun MaintenanceRow(record: MaintenanceEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = record.title, style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = record.details, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cost: $${"%.2f".format(record.cost)}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Date: ${record.date}", style = MaterialTheme.typography.body2)
        }
    }
}
