package com.example.imagescalerexample

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class ScaleAndCompressImage {

    fun scaleAndCompressImage(
        originalBitmap: Bitmap,
        desiredWidth: Int? = null,
        desiredHeight: Int? = null,
        targetFileSizeMB: Int = 1  // Target file size in MB
    ): ByteArray {

        val originalWidth = originalBitmap.width
        val originalHeight = originalBitmap.height
        val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

        val (scaledWidth, scaledHeight) = when {
            desiredWidth != null -> {
                val height = (desiredWidth / aspectRatio).toInt()
                desiredWidth to height
            }

            desiredHeight != null -> {
                val width = (desiredHeight * aspectRatio).toInt()
                width to desiredHeight
            }

            else -> originalWidth to originalHeight
        }

        val scaledBitmap =
            Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true)

        val targetFileSizeBytes = targetFileSizeMB * 1024 * 1024
        var quality = 100
        val byteArrayOutputStream = ByteArrayOutputStream()

        do {
            byteArrayOutputStream.reset()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            quality -= 5
        } while (byteArrayOutputStream.size() > targetFileSizeBytes && quality > 0)
        return byteArrayOutputStream.toByteArray()
    }
}