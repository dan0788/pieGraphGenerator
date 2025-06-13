package com.example.graficos_estadisticos_de_pastel.interfaces

import com.echo.holographlibrary.PieSlice

interface DrawInterface {
    fun graficarRebanada(rebanadas: ArrayList<PieSlice>, attribute: String, quantity: String): Boolean
    fun generarColorHexAleatorio() : String
}