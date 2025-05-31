package com.example.bakeryapp.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bakeryapp.TopScreenTitle
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType

@Composable
fun OrdersScreen(state: OrdersState, navController: NavController, onEvent: (OrdersEvent) -> Unit) {

    LaunchedEffect(Unit) {
        onEvent(OrdersEvent.loadData)
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        TopScreenTitle("Заказы")

        if (!state.ordersLoaded) {
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
            contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp),
        ) {
            items(
                items = state.orders,
            ) { order ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = appColors.background,
                            shape = RoundedCornerShape(16.dp),
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Заказ #${order.orderId}",
                            style = appType.inField
                        )
                        Text(
                            text = order.status,
                            style = when (order.status) {
                                "Completed" -> appType.inField.copy(color = appColors.green)
                                "Pending" -> appType.inField.copy(color = appColors.grey)
                                "Cancelled" -> appType.inField.copy(color = appColors.error)
                                else -> appType.inField
                            }
                        )
                    }

                    Text(
                        text = "${order.deliveryAddress}",
                        style = appType.inField
                    )

                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(thickness = 2.dp)

                    Spacer(Modifier.height(8.dp))

                    for (item in order.items) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.productName,
                                style = appType.base,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = "x${item.quantity}",
                                style = appType.base,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }


                }
            }
        }

        Button(
            onClick = { onEvent(OrdersEvent.leaveAccount) },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = appColors.error,
                disabledContainerColor = appColors.error,
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Выйти из аккаунта",
                style = appType.inButton
            )
        }
    }

}