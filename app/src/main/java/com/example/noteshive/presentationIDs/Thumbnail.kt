package com.example.noteshive.presentationIDs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap

class Thumbnail {

    suspend fun generatePdfThumbnailUri(context: Context, pdfUri: Uri?): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                val pfd = context.contentResolver.openFileDescriptor(pdfUri!!, "r") ?: return@withContext null
                val renderer = PdfRenderer(pfd)
                val page = renderer.openPage(0)

                // Adjust size as needed
                val width = 300
                val height = 300 //(page.height.toFloat() / page.width * width).toInt()
                val bitmap = createBitmap(width, height)
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                // Save bitmap to cache as image
                val file = File(context.cacheDir, "thumb_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                }

                page.close()
                renderer.close()
                pfd.close()

                Uri.fromFile(file) // Return image URI
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}