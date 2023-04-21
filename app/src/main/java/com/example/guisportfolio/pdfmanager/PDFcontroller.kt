package com.example.guisportfolio.pdfmanager

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.example.guisportfolio.R
import com.example.guisportfolio.ui.managers.AppUiState
import java.io.File
import java.io.FileOutputStream

class PdfController {

    //Handles Pdf creation, saving and sharing
    @RequiresApi(Build.VERSION_CODES.M)
    fun generatePDF(context: Context, uiState: AppUiState) {

        //Receiving local database from ui.State
        val movies = when (uiState) {
            is AppUiState.Loading -> emptyList()
            is AppUiState.Success -> uiState.movies
            is AppUiState.Error -> emptyList()
        }
        //Checking for Empty
        if (movies.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.empty_list_toast), Toast.LENGTH_SHORT).show()
            return
        }

        //Creating the document
        val document = pdfModel(context, movies)

        //Path and name to save
        val path = context.getExternalFilesDir(null)
        val filename = "portfolio_${System.currentTimeMillis()}.pdf"
        val file = File(path, filename)

        //Saving locally
        try {
            document.writeTo(FileOutputStream(file))
            Toast.makeText(context, context.getString(R.string.pdf_created_toast), Toast.LENGTH_LONG)
                .show()
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.pdf_error_toast), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

        //Getting URI
        val documentURI = FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", file
        )

        //Sharing and closing
        val share = Intent()
        share.setAction(Intent.ACTION_SEND)
        share.setType("application/pdf")
        share.putExtra(Intent.EXTRA_STREAM, documentURI)
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.grantUriPermission(
            "${context.packageName}.fileprovider",
            documentURI,
            Intent.FLAG_GRANT_READ_URI_PERMISSION.and(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        )
        startActivity(context, Intent.createChooser(share, "Share"), null)

        document.close()
    }
}


