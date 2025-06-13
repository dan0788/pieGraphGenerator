package com.example.graficos_estadisticos_de_pastel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.echo.holographlibrary.PieGraph
import com.echo.holographlibrary.PieSlice
import com.example.graficos_estadisticos_de_pastel.classes.DrawClass
import com.example.graficos_estadisticos_de_pastel.classes.DateClass
import com.example.graficos_estadisticos_de_pastel.databinding.ActivityMainBinding
import android.util.Log
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val rebanadas: ArrayList<PieSlice> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val draw = DrawClass()
        val year = DateClass()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener(){
            val attributeText = binding.editAttribute.text.toString().trim()
            val quantityText = binding.editCuantity.text.toString().trim()

            if (attributeText.isEmpty()) {
                binding.editAttribute.error = "El atributo no puede estar vacío"
                return@setOnClickListener
            }

            val success = draw.graficarRebanada(rebanadas, attributeText, quantityText)

            if(success){
                binding.graphTitle.text = "Gráfico de Pastel ${year.getCurrentYear()}"

                binding.graphPie.slices = ArrayList(rebanadas)
                binding.graphPie.postInvalidate()

                binding.editAttribute.text.clear()
                binding.editCuantity.text.clear()
                binding.editAttribute.requestFocus()
            }else{
                binding.editCuantity.error = "Por favor, introduce un número válido para la cantidad (ej. 10.5)"
                Log.e("MainActivity", "Error de entrada: Cantidad no válida.")
            }
        }

        binding.btnLimpiar.setOnClickListener {
            binding.graphTitle.text = ""
            rebanadas.clear()
            binding.graphPie.slices = rebanadas
            binding.graphPie.postInvalidate()
            Log.d("MainActivity", "Gráfico limpiado.")
            Toast.makeText(this, "Gráfico limpiado", Toast.LENGTH_SHORT).show()
        }

        binding.btnCambiarTitulo.setOnClickListener {
            val inputEditText = EditText(this)
            inputEditText.hint = "Nuevo título del gráfico"
            inputEditText.setText(binding.graphTitle.text)

            val container = LinearLayout(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(50, 0, 50, 0)
            container.layoutParams = params
            container.addView(inputEditText, params)

            AlertDialog.Builder(this).apply {
                setTitle("Cambiar Título del Gráfico")
                setView(container)

                setPositiveButton("Aceptar") { dialog, _ ->
                    val newTitle = inputEditText.text.toString().trim()
                    if (newTitle.isNotEmpty()) {
                        binding.graphTitle.text = newTitle
                        Toast.makeText(this@MainActivity, "Título actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }

                setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                show()
            }
        }

        binding.btnEliminarUltimo.setOnClickListener {
            if (rebanadas.isNotEmpty()) {
                rebanadas.removeAt(rebanadas.size - 1)
                val grafica = findViewById<PieGraph>(R.id.graphPie)
                grafica.slices = rebanadas
                grafica.postInvalidate()
                Log.d("MainActivity", "Último registro eliminado.")
                Toast.makeText(this, "Último registro eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No hay registros para eliminar", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity", "Intento de eliminar de gráfico vacío.")
            }
        }
    }
}