package edu.udb.dsm.desafio_practico_01

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class GradesActivity : AppCompatActivity() {
    private var resultOut: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_grades)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.grades_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultOut = findViewById(R.id.tvResult)

        // Obtener referencia al botón
        val btnCalcular: Button = findViewById(R.id.btnCalcular)

        // Configurar el evento de clic
        btnCalcular.setOnClickListener {
            calcularPromedio()
        }
    }

    private fun calcularPromedio() {
        // Obtiene el nombre del estudiante y verifica que no esté vacío
        val textInputLayout = findViewById<TextInputLayout>(R.id.textStudentName)
        val nombre = textInputLayout.editText?.text.toString().trim()

        if (nombre.isEmpty()) {
            resultOut?.text = "Ingrese el nombre del estudiante"
            return
        }
        // Mapeo de notas con sus respectivos pesos
        val pesos = listOf(0.15, 0.15, 0.20, 0.25, 0.25)
        val notasIds = listOf(
            R.id.tilNota1, R.id.tilNota2, R.id.tilNota3,
            R.id.tilNota4, R.id.tilNota5
        )

// Obtiene y valida las notas, luego calcula la suma ponderada
        val suma = notasIds.zip(pesos).sumOf { (id, peso) ->
            findViewById<TextInputLayout>(id).editText?.text.toString().trim().toDoubleOrNull()?.takeIf {
                it in 0.0..10.0
            }?.times(peso) ?: return Toast.makeText(this, "Ingrese notas válidas (entre 0 y 10)", Toast.LENGTH_SHORT).show().let { 0.0 }
        }

        // Calcula el promedio y determina el estado (umbral de aprobación: 6)
        val promedio = suma
        val estado = if (promedio >= 6.0) "Aprobado" else "Reprobado"

        // Formatear el resultado correctamente
        val resultado = """
    Estudiante: $nombre
    Promedio: ${"%.2f".format(promedio)}
    Estado: $estado
""".trimIndent()

        resultOut?.text = resultado
    }
}