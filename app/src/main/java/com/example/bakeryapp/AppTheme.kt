package com.example.bakeryapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides appColors,
        LocalType provides appType,
    ) {
        content()
    }
}

data class TextColors(
    val background: Color,
    val primaryText: Color,
    val lightGrey: Color,
    val border: Color,
    val grey: Color,
    val blue: Color,
    val error: Color,
    val green: Color,
)

val appColors = TextColors(
    background = Color(0xFFFFFFFF),
    primaryText = Color(0xFF030303),
    lightGrey = Color(0xFFF6F6F6),
    border = Color(0xFF5D4037),
    grey = Color(0xFF8E8E8E),
    blue = Color(0xFF3266C6),
    error = Color(0xFFAB2453),
    green = Color(0xFF34e056),
)

data class TextType(
    val title: TextStyle,
    val subTitle: TextStyle,
    val inField: TextStyle,
    val inButton: TextStyle,
    val base: TextStyle,
    val navItem: TextStyle,
    val categoryInverse: TextStyle,
    val titleProduct: TextStyle,
    val priceProduct: TextStyle,
    val order: TextStyle,
    val error: TextStyle,
)

val appType = TextType(
    title = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = appColors.primaryText
    ),
    subTitle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.primaryText
    ),
    inField = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = appColors.primaryText
    ),
    inButton = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = appColors.background
    ),
    base = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.primaryText
    ),
    navItem = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.primaryText
    ),
    categoryInverse = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.background
    ),

    titleProduct = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.grey
    ),
    priceProduct = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = appColors.primaryText
    ),
    order = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = appColors.background
    ),
    error = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = appColors.error
    ),

    )

internal val LocalColors = staticCompositionLocalOf { appColors }
internal val LocalType = staticCompositionLocalOf { appType }



