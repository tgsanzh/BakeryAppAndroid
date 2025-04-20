package com.example.bakeryapp.confirm.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bakeryapp.R
import com.example.bakeryapp.appColors
import com.example.bakeryapp.appType
import com.example.bakeryapp.utils.CodeVisualTransformation

@Composable
fun ConfirmScreen(number: String, state: ConfirmState, onEvent: (ConfirmEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onEvent(ConfirmEvent.back)
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Код подтверждения",
                style = appType.title
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Отправили код на номер +7${number}",
                style = appType.subTitle
            )
            Spacer(Modifier.height(14.dp))


            TextField(
                value = state.number,
                onValueChange = {
                    val digitsOnly = it.filter { char -> char.isDigit() }.take(4)
                    onEvent(ConfirmEvent.onValueChanged(digitsOnly))
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(start = 10.dp)
                    .align(Alignment.CenterHorizontally),
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = appColors.background,
                    unfocusedContainerColor = appColors.background,
                    cursorColor = appColors.border
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                visualTransformation = CodeVisualTransformation(),
                singleLine = true,
                textStyle = TextStyle(
                    letterSpacing = 0.sp,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )


            Spacer(Modifier.height(14.dp))

            Button(
                onClick = {},
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
                    text = "Отправить код",
                    style = appType.inButton
                )
            }

        }



    }
}