package com.cobrien.myjournal

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Serializable
data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    val modifiedAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
    val backgroundColor: Long = 0xFFFFF8E1.toInt().toLong(), // Warm cream default
    val textColor: Long = 0xFF5D4037.toInt().toLong(), // Warm brown default
    val fontSize: Int = 16,
    val drawingData: String? = null
)

@Serializable
data class UserPreferences(
    val defaultBackgroundColor: Long = 0xFFFFF8E1.toInt().toLong(), // Warm cream
    val defaultTextColor: Long = 0xFF5D4037.toInt().toLong(), // Warm brown
    val defaultFontSize: Int = 16,
    val enableHandwriting: Boolean = true
)

fun Long.toComposeColor(): Color {
    // Convert stored Long (ARGB format) back to Color
    // Extract ARGB components from Int
    val argb = this.toInt()
    val a = ((argb shr 24) and 0xFF) / 255f
    val r = ((argb shr 16) and 0xFF) / 255f
    val g = ((argb shr 8) and 0xFF) / 255f
    val b = (argb and 0xFF) / 255f
    return Color(red = r, green = g, blue = b, alpha = a)
}

fun Color.toLongValue(): Long {
    // Convert Color to ARGB Int, then to Long for storage
    val a = (this.alpha * 255).toInt() and 0xFF
    val r = (this.red * 255).toInt() and 0xFF
    val g = (this.green * 255).toInt() and 0xFF
    val b = (this.blue * 255).toInt() and 0xFF
    val argb = (a shl 24) or (r shl 16) or (g shl 8) or b
    return argb.toLong()
}
