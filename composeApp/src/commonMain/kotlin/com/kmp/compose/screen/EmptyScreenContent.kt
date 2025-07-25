package com.kmp.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinmultiplatformproject.composeapp.generated.resources.Res
import kotlinmultiplatformproject.composeapp.generated.resources.compose_multiplatform
import kotlinmultiplatformproject.composeapp.generated.resources.no_data_available
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun EmptyScreenContent(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null
            )

            Spacer(Modifier.height(16.dp))

            Text(stringResource(Res.string.no_data_available))
        }
    }

}
