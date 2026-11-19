package cl.duoc.leoley.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.provider.MediaStore
import android.text.StaticLayout
import android.text.TextPaint
import androidx.core.app.NotificationCompat
import cl.duoc.leoley.model.Ley

class PdfRepository(private val context: Context) {

    // Crea el PDF de la ley y lo guarda en Descargas. Devuelve true si salió bien
    fun descargarLey(ley: Ley): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return false
        }

        try {
            val pdf = PdfDocument()
            val pagina = pdf.startPage(PdfDocument.PageInfo.Builder(595, 842, 1).create())
            val canvas = pagina.canvas

            val pincelTitulo = TextPaint().apply {
                textSize = 26f
                isFakeBoldText = true
            }
            val pincelTexto = TextPaint().apply {
                textSize = 14f
            }

            canvas.drawText("Ley ${ley.numero}", 50f, 80f, pincelTitulo)
            canvas.drawText(ley.nombre, 50f, 115f, pincelTexto)
            canvas.drawText("Año de publicación: ${ley.anio}", 50f, 145f, pincelTexto)

            // drawText no baja de línea solo, así que la descripción usa StaticLayout
            val descripcion = StaticLayout.Builder
                .obtain(ley.descripcion, 0, ley.descripcion.length, pincelTexto, 495)
                .build()
            canvas.save()
            canvas.translate(50f, 185f)
            descripcion.draw(canvas)
            canvas.restore()

            pdf.finishPage(pagina)

            val nombreArchivo = "Ley_${ley.numero.replace(".", "")}.pdf"
            val valores = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, nombreArchivo)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            }
            val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, valores)
            if (uri == null) {
                pdf.close()
                return false
            }

            context.contentResolver.openOutputStream(uri)?.use { salida ->
                pdf.writeTo(salida)
            }
            pdf.close()

            // NUEVO: avisa con una notificación que la descarga terminó
            mostrarNotificacion(nombreArchivo)
            return true
        } catch (e: Exception) {
            return false
        }
    }
    private fun mostrarNotificacion(nombreArchivo: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // El canal es la "categoría" de la notificación (obligatorio desde Android 8)
        val canal = NotificationChannel("descargas", "Descargas", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(canal)

        val notificacion = NotificationCompat.Builder(context, "descargas")
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentTitle("Descarga exitosa")
            .setContentText("$nombreArchivo guardado en Descargas")
            .setAutoCancel(true)
            .build()

        // El número distinto por archivo evita que una notificación pise a otra
        manager.notify(nombreArchivo.hashCode(), notificacion)
    }
}