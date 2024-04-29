package com.tidal.sdk.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.tidal.sdk.auth.model.DeviceAuthorizationResponse
import com.tidal.sdk.common.d
import com.tidal.sdk.common.getLoggerByName
import kotlinx.coroutines.launch

private sealed class State {
    data object Init : State()
    class Link(val response: DeviceAuthorizationResponse) : State()
    data object Done : State()
}

@Composable
fun DeviceLoginScreen() {
    val activity = LocalContext.current.findActivity() as MainActivity
    val scope = rememberCoroutineScope()
    val state = remember { mutableStateOf<State>(State.Init) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        when (state.value) {
            is State.Init -> {
                InitUI {
                    scope.launch {
                        activity.initializeDeviceLogin().successData?.let {
                            state.value = State.Link(it)
                        }
                            ?: getLoggerByName(
                                "DeviceLoginScreen"
                            ).d { "error initiating device login" }
                    }
                }
            }

            is State.Link -> {
                with((state.value as State.Link).response) {
                    LinkUI(this.verificationUri, this.userCode)
                    val deviceCode = this.deviceCode
                    scope.launch {
                        activity.finalizeDeviceLogin(deviceCode)
                    }
                }
            }

            is State.Done -> {
            }
        }
    }
}

@Composable
fun InitUI(onClick: () -> Unit) {
    Column {
        Button(
            onClick = onClick,
        ) {
            Text(text = "Start Device Login")
        }
    }
}

@Composable
fun LinkUI(uri: String, code: String) {
    Column {
        Text(
            text = "$code",
            color = Color.White,
        )
        Text(
            text = "$uri",
            color = Color.White,
        )
    }
}
