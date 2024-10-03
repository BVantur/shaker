package sp.bvantur.inspektify.ktor.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import sp.bvantur.inspektify.ktor.core.di.AppComponents
import sp.bvantur.inspektify.ktor.core.presentation.viewModelFactory
import sp.bvantur.inspektify.ktor.data.model.NetworkTraffic
import sp.bvantur.inspektify.ktor.presentation.overview.NetworkTrafficDetailsOverviewViewModel
import sp.bvantur.inspektify.ktor.presentation.overview.NetworkTrafficDetailsOverviewViewState

@Composable
internal fun NetworkTrafficDetailsOverviewRoute(networkTraffic: NetworkTraffic?, modifier: Modifier = Modifier) {
    val viewModel = viewModel<NetworkTrafficDetailsOverviewViewModel>(
        factory = viewModelFactory {
            NetworkTrafficDetailsOverviewViewModel(
                dispatcherProvider = AppComponents.getDispatcherProvider()
            )
        }
    )
    val viewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(networkTraffic) {
        viewModel.preparePresentationData(networkTraffic)
    }

    NetworkTrafficDetailsOverviewScreen(
        viewState = viewState,
        modifier = modifier
    )
}

@Composable
private fun NetworkTrafficDetailsOverviewScreen(
    viewState: NetworkTrafficDetailsOverviewViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        with(viewState) {
            url?.let {
                OverviewContentRow("URL", it)
            }
            method?.let {
                OverviewContentRow("Method", it)
            }
            protocol?.let {
                OverviewContentRow("Protocol", it)
            }
            status?.let {
                OverviewContentRow("Status", it)
            }
            response?.let {
                OverviewContentRow("Response", it)
            }
            ssl?.let {
                OverviewContentRow("SSL", it)
            }
            requestTime?.let {
                OverviewContentRow("Request time", it)
            }
            responseTime?.let {
                OverviewContentRow("Response time", it)
            }
            duration?.let {
                OverviewContentRow("Duration", it)
            }
            requestSize?.let {
                OverviewContentRow("Request size", it)
            }
            responseSize?.let {
                OverviewContentRow("Response size", it)
            }
            totalSize?.let {
                OverviewContentRow("Total size", it)
            }
        }
    }
}

@Composable
private fun OverviewContentRow(propertyName: String, propertyValue: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        Text(
            text = propertyName,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = propertyValue,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(2f)
        )
    }
}
