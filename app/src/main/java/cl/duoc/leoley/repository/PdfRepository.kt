package cl.duoc.leoley.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.provider.MediaStore
import android.text.StaticLayout
import android.text.TextPaint
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

            val valores = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "Ley_${ley.numero.replace(".", "")}.pdf")
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
            return true
        } catch (e: Exception) {
            return false
        }
    }
}