package com.example.bakeryapp.catalog.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bakeryapp.R
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType

@Composable
fun CatalogScreen(
    state: CatalogState,
    navController: NavController,
    onEvent: (CatalogEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(CatalogEvent.loadCategory)
        onEvent(CatalogEvent.loadCatalog)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        TextField(
            value = state.search,
            onValueChange = {
                onEvent(CatalogEvent.onSearchChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, appColors.border, RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = appColors.background,
                unfocusedContainerColor = appColors.background,
                cursorColor = appColors.border
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = Modifier.padding(start = 8.dp),
                    tint = appColors.border
                )
            },
            singleLine = true,
            textStyle = appType.base,
        )

        Spacer(Modifier.height(16.dp))

        if (!state.productsLoaded && !state.categoryLoaded) {
            CircularProgressIndicator(
                color = appColors.border,
                modifier = Modifier
                    .size(36.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)

        ) {
            items(
                items = state.categories
            ) { category ->
                Text(
                    text = category.name,
                    style = if (state.category == category.id)
                        appType.categoryInverse else appType.base,
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onEvent(CatalogEvent.onCatalogChanged(category.id))
                        }
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(12.dp),
                            color = appColors.border
                        )
                        .background(
                            color = if (state.category == category.id)
                                appColors.border else appColors.background
                        )
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                )
            }
        }

//        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            items(
                items = state.products,
            ) { item ->
                if ((state.category == -1 || item.category_id == state.category) && item.title.contains(
                        state.search
                    )
                ) {
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(136.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = appColors.background,
                                shape = RoundedCornerShape(16.dp),
                            )
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "${item.price} KZT",
                                style = appType.priceProduct,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                            Text(
                                text = item.title,
                                style = appType.titleProduct,
                                maxLines = 5,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Column(
                            modifier = Modifier

                                .padding(8.dp)
                                .width(36.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onEvent(CatalogEvent.onOrder(item.id))
                                }
                                .background(
                                    color = appColors.blue,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.5.dp)
                        ) {
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = "зать",
                                style = appType.order,
                                modifier = Modifier
                                    .rotate(-90f)
                            )
                            Text(
                                text = "Зака",
                                style = appType.order,
                                modifier = Modifier
                                    .rotate(-90f)
                            )
                        }
                    }
                }
            }

        }

    }
}
