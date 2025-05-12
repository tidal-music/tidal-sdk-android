package com.tidal.sdk.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventSenderDemoScreen(onButtonClick: () -> Unit) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "The name of this TIDAL module is: EventProducer!",
                )
                Button(
                    modifier = Modifier.padding(vertical = 100.dp),
                    onClick = { onButtonClick() },
                ) {
                    Text(text = "Generate new event")
                }
            }
        }
    }
}
