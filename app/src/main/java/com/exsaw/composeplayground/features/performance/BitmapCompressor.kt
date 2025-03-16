package com.exsaw.composeplayground.features.performance

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.exsaw.composeplayground.core.IDispatchersProvider
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class BitmapCompressor(
    private val context: Context,
    private val dispatchers: IDispatchersProvider,
) {
    suspend fun compressImage(
        contentUri: Uri,
        compressionThreshold: Long
    ): Bitmap? = withContext(dispatchers.io) {

        val inputBytes = context
            .contentResolver
            .openInputStream(contentUri)?.use { inputStream ->
                inputStream.readBytes()
            } ?: return@withContext null

        val bitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size)

        var outputBytes: ByteArray
        var quality = 100
        do {
            ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                outputBytes = outputStream.toByteArray()
                quality -= (quality * 0.1).roundToInt()
            }
        } while (outputBytes.size > compressionThreshold && quality > 5)

        BitmapFactory.decodeByteArray(outputBytes, 0, outputBytes.size)
    }
}