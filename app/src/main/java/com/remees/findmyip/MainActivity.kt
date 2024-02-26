package com.remees.findmyip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remees.findmyip.feature_findip.data.remote.dto.IpInfoDto
import com.remees.findmyip.feature_findip.presentation.FindMyIpViewModel
import com.remees.findmyip.ui.theme.FindMyIPTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FindMyIPTheme {

                val viewModel: FindMyIpViewModel = hiltViewModel()
                val state = viewModel.state.value
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is FindMyIpViewModel.UIEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center){
                        FindMyIpLayout(state.ipInfoItems)

                        if(state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun FindMyIpLayout(ipInfoDto: IpInfoDto?, modifier: Modifier = Modifier) {
    Column {

        ipInfoDto?.ip?.let { RowItems("IP", it) }
        ipInfoDto?.countryName?.let { RowItems("Country",it) }
        ipInfoDto?.network?.let { RowItems("Network", it) }
        ipInfoDto?.version?.let { RowItems("Version", it) }
        ipInfoDto?.city?.let { RowItems("City", it) }
        ipInfoDto?.region?.let { RowItems("Region", it) }
        ipInfoDto?.regionCode?.let { RowItems("Region Code", it) }

    }
}

@Composable
fun RowItems(name: String, value: String) {
    Row (modifier = Modifier.padding(10.dp)){

        Text(
            modifier = Modifier.width(100.dp),
            fontWeight = FontWeight.Bold,
            text = name
            )
        Text(
            modifier = Modifier.width(10.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            text = ":",
            )
        Text(
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            text = value
        )
    }
}