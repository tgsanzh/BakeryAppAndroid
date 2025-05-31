package com.example.bakeryapp.register.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bakeryapp.R
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType
import com.example.bakeryapp.utils.PhoneVisualTransformation

@Composable
fun RegisterScreen(state: RegisterState, onEvent: (RegisterEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Регистрация",
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
                        onEvent(RegisterEvent.onNumberChanged(digitsOnly))
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
                    onEvent(RegisterEvent.onPasswordChanged(it))
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
                placeholder = {
                    Text(
                        text = "Введите пароль...",
                        style = appType.inField.copy(color = appColors.grey)
                    )
                },
                singleLine = true,
                textStyle = appType.inField,
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                value = state.confirmPassword,
                onValueChange = {
                    onEvent(RegisterEvent.onConfirmPasswordChanged(it))
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
                placeholder = {
                    Text(
                        text = "Повторите пароль...",
                        style = appType.inField.copy(color = appColors.grey)
                    )
                },
                singleLine = true,
                textStyle = appType.inField,
            )

            if (state.showError) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.errorText,
                    style = appType.error,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onEvent(RegisterEvent.register(state.number)) },
                enabled = state.buttonEnabled,
                modifier = Modifier
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
                        text = "Зарегестрироваться",
                        style = appType.inButton
                    )
                else
                    CircularProgressIndicator(
                        color = appColors.background,
                    )
            }

        }

        Text(
            text = "У меня уже есть аккаунт",
            style = appType.inField.copy(color = appColors.blue),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onEvent(RegisterEvent.toLogin)
                }
        )
    }
}