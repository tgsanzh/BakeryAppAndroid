package com.example.bakeryapp.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bakeryapp.R
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType
import com.example.bakeryapp.catalog.presentation.CatalogEvent
import com.example.bakeryapp.utils.PhoneVisualTransformation

@Composable
fun LoginScreen(state: LoginState, onEvent: (LoginEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Вход",
                style = appType.title
            )

            Spacer(Modifier.height(20.dp))

            Row {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .width(68.dp)
                        .background(color = appColors.lightGrey, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.kz_flag),
                        contentDescription = "Flag of Kazakhstan",
                        modifier = Modifier
                            .size(48.dp, 36.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                TextField(
                    value = state.number,
                    onValueChange = {
                        val digitsOnly = it.filter { char -> char.isDigit() }.take(10)
                        onEvent(LoginEvent.onNumberChanged(digitsOnly))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
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
                        Text(
                            text = "+7",
                            style = appType.inField
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    visualTransformation = PhoneVisualTransformation(),
                    singleLine = true,
                    textStyle = appType.inField
                )
            }

            Spacer(Modifier.height(12.dp))

            TextField(
                value = state.password,
                onValueChange = {
                    onEvent(LoginEvent.onPasswordChanged(it))
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
//                leadingIcon = {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_search),
//                        contentDescription = "Search",
//                        modifier = Modifier.padding(start = 8.dp),
//                        tint = appColors.border
//                    )
//                },
                placeholder = {
                    Text(
                        text = "Введите пароль...",
                        style = appType.inField.copy(color = appColors.grey)
                    ) },
                singleLine = true,
                textStyle = appType.inField,
            )

            if(state.showError) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.errorText,
                    style = appType.error
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onEvent(LoginEvent.login(state.number)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = appColors.border,
                    disabledContainerColor = appColors.border,
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Войти",
                    style = appType.inButton
                )
            }

        }



    }
}