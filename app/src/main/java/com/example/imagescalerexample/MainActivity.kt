package com.example.imagescalerexample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val scaleAndCompressImage = ScaleAndCompressImage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the original Image
        val originalImage =
            BitmapFactory.decodeResource(resources, R.drawable.image)

        val thumbnailWidth = 150
        val thumbnailHeight = 150
        val targetFileSizeMB = 1
        val compressedImageBytes = scaleAndCompressImage.scaleAndCompressImage(
            originalImage,
            getWidth = thumbnailWidth,
            getHeight = thumbnailHeight,
            targetFileSizeMB = targetFileSizeMB
        )

        val compressedImage = BitmapFactory.decodeByteArray(
            compressedImageBytes, 0,
            compressedImageBytes.size
        )
        // display compressed image
        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageBitmap(compressedImage)
    }
}