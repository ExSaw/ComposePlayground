package com.exsaw.composeplayground.tool


fun logUnlimited(
    string: String,
    maxLogSize: Int = 4000,
) {
    if (string.isNotEmpty()) {
        println(
            string.replace("\n+".toRegex(), replacement = " ")
                .chunked(maxLogSize)
                .joinToString(separator = "\n${string.first()}${string.first()}${string.first()}>>")
                .trimIndent()
        )
    }
}