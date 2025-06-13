package com.example.graficos_estadisticos_de_pastel.classes

import com.example.graficos_estadisticos_de_pastel.interfaces.DateInterface
import java.util.Calendar

class DateClass : DateInterface{
    override fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }
}