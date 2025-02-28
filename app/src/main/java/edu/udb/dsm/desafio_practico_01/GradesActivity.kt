package edu.udb.dsm.desafio_practico_01

import android.annotation.SuppressLint
import android.os.Bundle
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
    }

    private fun calcularPromedio() {
        // Obtiene el nombre del estudiante y verifica que no esté vacío
        val nombre: String = findViewById<TextInputLayout>(R.id.textStudentName).toString().trim()
        if (nombre.isEmpty()) {
            resultOut?.text = "Ingrese el nombre del estudiante"
            return
        }

        // Obtiene y valida cada nota con su respectivo peso
        val notasConPesos = listOf(
            Pair(binding.etNota1.text.toString().trim(), 0.15),
            Pair(binding.etNota2.text.toString().trim(), 0.15),
            Pair(binding.etNota3.text.toString().trim(), 0.20),
            Pair(binding.etNota4.text.toString().trim(), 0.25),
            Pair(binding.etNota5.text.toString().trim(), 0.25)
        )

        var suma = 0.0

        for ((notaStr, peso) in notasConPesos) {
            val nota = notaStr.toDoubleOrNull()
            if (nota == null || nota < 0.0 || nota > 10.0) {
                Toast.makeText(this, "Ingrese notas válidas (entre 0 y 10)", Toast.LENGTH_SHORT).show()
                return
            }
            suma += nota * peso
        }

        // Calcula el promedio y determina el estado (umbral de aprobación: 6)
        val promedio = suma
        val estado = if (promedio >= 6.0) "Aprobado" else "Reprobado"

        // Muestra el resultado en el TextView
        resultOut?.text = "Estudiante: $nombre\nPromedio: ${"%.2f".format(promedio)}\nEstado: $estado"
    }
}