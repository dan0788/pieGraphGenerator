package com.example.graficos_estadisticos_de_pastel.classes
import com.echo.holographlibrary.Bar
import com.echo.holographlibrary.PieSlice
import com.example.graficos_estadisticos_de_pastel.interfaces.DrawInterface
import android.graphics.Color
import java.util.Random
import android.util.Log

class DrawClass : DrawInterface {

    override fun graficarRebanada(
        rebanadas: ArrayList<PieSlice>,
        attribute: String,
        quantity: String
    ): Boolean {
        try {
            val slice = PieSlice()
            val color = generarColorHexAleatorio()
            slice.color = Color.parseColor(color)
            slice.title = attribute
            slice.value = quantity.toFloat()
            rebanadas.add(slice)

            return true
        } catch (e: NumberFormatException) {
            Log.e(
                "Draw",
                "Error al convertir la cantidad '$quantity' a Float para rebanada: ${e.message}",
                e
            )
            return false
        }
    }

    override fun generarColorHexAleatorio(): String {
        val random = Random()

        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)

        return String.format("#%02X%02X%02X", red, green, blue)
    }
}