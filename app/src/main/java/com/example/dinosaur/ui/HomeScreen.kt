package com.example.dinosaur.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinosaur.R
import com.example.dinosaur.network.DinosaurPhoto
import com.example.dinosaur.ui.theme.DinosaursTheme
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeScreen(
    dinosaurUiState: DinosaursUiState, retryAction: () -> Unit, modifier: Modifier = Modifier,
) {
    when (dinosaurUiState) {
        is DinosaursUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is DinosaursUiState.Success -> PhotosColumnScreen(
            photos = dinosaurUiState.photos, modifier = modifier
        )

        is DinosaursUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun PhotosColumnScreen(photos: List<DinosaurPhoto>, modifier: Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        items(items = photos, key = { photo -> photo.name }) { photo ->
            DinosaurPhotoCard(photo = photo)
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "loading"
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Error", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}


@Composable
fun DinosaurPhotoCard(photo: DinosaurPhoto, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(4.dp).fillMaxWidth()) {
        Column {
            Row {
                Text(text = "${photo.name}  (${photo.length})", style = MaterialTheme.typography.titleLarge,  fontWeight = FontWeight.Bold, textAlign = TextAlign.Start, modifier = Modifier.padding(16.dp))
            }

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(photo.imgSrc)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = modifier.fillMaxWidth()
            )
            Text(text = photo.description, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Justify, modifier = Modifier.padding(16.dp))
        }
    }
}