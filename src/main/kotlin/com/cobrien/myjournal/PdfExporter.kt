package com.cobrien.myjournal

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.io.font.constants.StandardFonts
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

data class ExportOptions(
    val includeTitle: Boolean = true,
    val includeDate: Boolean = true,
    val includeContent: Boolean = true
)

enum class ExportAction {
    SAVE_TO_DOWNLOADS,
    SAVE_AND_EMAIL
}

class PdfExporter {
    
    fun exportToPdf(
        entry: JournalEntry,
        outputFile: File,
        options: ExportOptions = ExportOptions()
    ): Boolean {
        return try {
            val writer = PdfWriter(FileOutputStream(outputFile))
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)
            
            val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)
            val regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)
            val italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE)
            
            // Add title
            if (options.includeTitle) {
                val titleParagraph = Paragraph(entry.title)
                    .setFont(boldFont)
                    .setFontSize(20f)
                    .setMarginBottom(10f)
                document.add(titleParagraph)
            }
            
            // Add date
            if (options.includeDate) {
                val dateStr = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
                    .format(Date(System.currentTimeMillis()))
                val dateParagraph = Paragraph(dateStr)
                    .setFont(italicFont)
                    .setFontSize(12f)
                    .setMarginBottom(20f)
                document.add(dateParagraph)
            }
            
            // Add content
            if (options.includeContent) {
                val lines = entry.content.split("\n")
                
                for (line in lines) {
                    val paragraph = Paragraph(line.ifEmpty { " " })
                        .setFont(regularFont)
                        .setFontSize(entry.fontSize.toFloat())
                        .setMarginBottom(5f)
                    document.add(paragraph)
                }
            }
            
            document.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun getDownloadsFolder(): File {
        val userHome = System.getProperty("user.home")
        val downloadsDir = File(userHome, "Downloads")
        
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }
        
        return downloadsDir
    }
    
    fun createPdfFileName(entry: JournalEntry): String {
        val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date(System.currentTimeMillis()))
        val safeTitle = entry.title
            .replace(Regex("[^a-zA-Z0-9\\s]"), "_")
            .replace(Regex("\\s+"), "_")
            .take(30)
        return "${dateStr}_${safeTitle}.pdf"
    }
    
    fun saveToDownloads(
        entry: JournalEntry,
        options: ExportOptions = ExportOptions()
    ): File? {
        return try {
            val fileName = createPdfFileName(entry)
            val outputFile = File(getDownloadsFolder(), fileName)
            
            if (exportToPdf(entry, outputFile, options)) {
                outputFile
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
