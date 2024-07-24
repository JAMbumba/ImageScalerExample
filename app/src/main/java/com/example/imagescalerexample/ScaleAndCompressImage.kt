package com.example.imagescalerexample

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class ScaleAndCompressImage {

    fun scaleAndCompressImage(
        originalBitmap: Bitmap,
        getWidth: Int? = null,
        getHeight: Int? = null,
        targetFileSizeMB: Int = 1  //target file size in MB
    ): ByteArray {

        val originalImageWidth = originalBitmap.width
        val originalImageHeight = originalBitmap.height
        val aspectRatio = originalImageWidth.toFloat() / originalImageHeight.toFloat()

        val (scaledWidth, scaledHeight) = when {
            getWidth != null -> {
                val height = (getWidth / aspectRatio).toInt()
                getWidth to height
            }

            getHeight != null -> {
                val width = (getHeight * aspectRatio).toInt()
                width to getHeight
            }

            else -> originalImageWidth to originalImageHeight
        }

        val scaledImage =
            Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true)

        val targetFileSizeBytes = targetFileSizeMB * 1024 * 1024
        var quality = 100
        val byteArrayOutputStream = ByteArrayOutputStream()

        do {
            byteArrayOutputStream.reset()
            scaledImage.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            quality -= 5
        } while (byteArrayOutputStream.size() > targetFileSizeBytes && quality > 0)
        return byteArrayOutputStream.toByteArray()
    }
}