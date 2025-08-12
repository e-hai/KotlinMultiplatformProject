package com.kmp.compose.screen.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kmp.compose.data.Museum
import com.kmp.compose.screen.EmptyScreenContent
import com.kmp.compose.utils.Log
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.gallery.GALLERY
import dev.icerock.moko.permissions.storage.STORAGE
import dev.icerock.moko.permissions.storage.WRITE_STORAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigateToDetails: (objectId: Long) -> Unit
) {
    val viewModel: ListViewModel = viewModel()
    val objects by viewModel.objects.collectAsStateWithLifecycle()

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    AnimatedContent(objects.isNotEmpty()) { objectsAvailable ->
        if (objectsAvailable) {
            ObjectGrid(
                objects = objects,
                onObjectClick = { id ->
                    coroutineScope.launch {
                        try {
                            controller.providePermission(Permission.CAMERA)
                            // Permission has been granted successfully.
                            Log.d("Permissions", "Camera permission granted")
                            navigateToDetails(id)
                        } catch (deniedAlways: DeniedAlwaysException) {
                            // Permission is always denied.
                            Log.wtf("Permissions", "Camera permission denied always", deniedAlways)
                        } catch (denied: DeniedException) {
                            // Permission was denied.
                            Log.wtf("Permissions", "Camera permission denied", denied)
                        }
                    }
                },
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ObjectGrid(
    objects: List<Museum>,
    onObjectClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(objects, key = { it.objectID }) { obj ->
            ObjectFrame(
                obj = obj,
                onClick = { onObjectClick(obj.objectID) },
            )
        }
    }
}

@Composable
private fun ObjectFrame(
    obj: Museum,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = obj.primaryImageSmall,
            contentDescription = obj.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(obj.title, style = MaterialTheme.typography.titleMedium)
        Text(obj.artistDisplayName, style = MaterialTheme.typography.bodyMedium)
        Text(obj.objectDate, style = MaterialTheme.typography.bodySmall)
    }
}
