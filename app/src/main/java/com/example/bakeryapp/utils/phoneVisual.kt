package com.example.bakeryapp.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawInput = text.text.filter { it.isDigit() }
        val masked = rawInput.padEnd(10, '0')

        val builder = AnnotatedString.Builder()

        for (i in masked.indices) {
            val char = masked[i]
            val isPlaceholder = i >= rawInput.length

            builder.withStyle(
                style = SpanStyle(color = if (isPlaceholder) Color(0xFFA9A9AA) else Color(0xFF030303))
            ) {
                append(char)
            }

            when (i) {
                2, 5, 7 -> builder.withStyle(SpanStyle(color = Color(0xFFA9A9AA))) { append(" ") }
            }
        }

        val transformed = builder.toAnnotatedString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset + 1
                    offset <= 7 -> offset + 2
                    offset <= 10 -> offset + 3
                    else -> 13
                }.coerceAtMost(transformed.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                val original = when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset - 1
                    offset <= 10 -> offset - 2
                    offset <= 13 -> offset - 3
                    else -> 10
                }
                return original.coerceIn(0, rawInput.length)
            }
        }

        return TransformedText(transformed, offsetMapping)
    }
}


class CodeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val rawInput = text.text.filter { it.isDigit() }
        val masked = rawInput.padEnd(4, '0')

        val builder = AnnotatedString.Builder()

        for (i in masked.indices) {
            val char = masked[i]
            val isPlaceholder = i >= rawInput.length

            builder.withStyle(
                style = SpanStyle(color = if (isPlaceholder) Color(0xFFA9A9AA) else Color(0xFF030303))
            ) {
                append(char)
            }

            if (i != masked.lastIndex) {
                builder.withStyle(SpanStyle(color = Color(0xFFA9A9AA))) {
                    append(" ")
                }
            }
        }

        val transformed = builder.toAnnotatedString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return (offset * 2).coerceAtMost(transformed.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return (offset / 2).coerceIn(0, rawInput.length)
            }
        }

        return TransformedText(transformed, offsetMapping)
    }
}
