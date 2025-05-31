package com.example.bakeryapp.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bakeryapp.R
import com.example.bakeryapp.TopScreenTitle
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType

@Composable
fun CartScreen(state: CartState, navController: NavController, onEvent: (CartEvent) -> Unit) {
    LaunchedEffect(Unit) {
        onEvent(CartEvent.loadCarts)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopScreenTitle("Корзина")
            if (!state.cartLoaded) {
                CircularProgressIndicator(
                    color = appColors.border,
                    modifier = Modifier
                        .size(36.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
            ) {

                items(
                    items = state.cartItems,
                ) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(148.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = appColors.background,
                                shape = RoundedCornerShape(16.dp),
                            )
                    ) {
                        AsyncImage(
                            model = item.short_product.imageUrl,
                            contentDescription = item.short_product.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(132.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "${item.short_product.price} KZT",
                                style = appType.priceProduct,
                                modifier = Modifier.padding(top = 12.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = item.short_product.title,
                                style = appType.titleProduct,
                                maxLines = 3,
                                modifier = Modifier.weight(1f),
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                                    .background(
                                        color = appColors.background,
                                        shape = RoundedCornerShape(12.dp),
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .align(Alignment.End),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_minus),
                                    contentDescription = "Minus",
                                    tint = appColors.blue,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable {
                                            onEvent(CartEvent.minus(item))
                                        }
                                )
                                Text(
                                    text = item.quantity.toString(),
                                    style = appType.priceProduct,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp)
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_plus),
                                    contentDescription = "Plus",
                                    tint = appColors.blue,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable {
                                            onEvent(CartEvent.plus(item))
                                        }
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(thickness = 2.dp, color = appColors.grey)
            Text(
                text = "Общяя сумма заказа: ${state.sum}",
                style = appType.base,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
            TextField(
                value = state.deliveryAdress,
                onValueChange = {
                    onEvent(CartEvent.adressChanged(it))
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
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
                placeholder = {
                    Text(
                        text = "Введите адресс...",
                        style = appType.inField.copy(color = appColors.grey)
                    )
                },
                singleLine = true,
                textStyle = appType.inField,
            )

            if (state.showError) {
                Text(
                    text = state.errorText,
                    style = appType.error,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            Button(
                onClick = { onEvent(CartEvent.onOrder) },
                enabled = state.cartItems.isNotEmpty() && state.buttonEnabled,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = appColors.border,
                    disabledContainerColor = appColors.border,
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (state.buttonEnabled)
                    Text(
                        text = "Оформить заказ",
                        style = appType.inButton
                    )
                else
                    CircularProgressIndicator(
                        color = appColors.background,
                    )
            }
        }


    }
}