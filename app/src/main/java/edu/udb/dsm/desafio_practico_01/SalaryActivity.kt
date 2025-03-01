package edu.udb.dsm.desafio_practico_01

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class SalaryActivity : AppCompatActivity() {
    private lateinit var etNombre: TextInputEditText
    private lateinit var etSalario: TextInputEditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.salary_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización con findViewById
        etNombre = findViewById(R.id.etNombre)
        etSalario = findViewById(R.id.etSalario)
        btnCalcular = findViewById(R.id.btnCalcular)
        tvResultado = findViewById(R.id.tvResultado)

        btnCalcular.setOnClickListener {
            calcularDescuentos()
        }
    }

    private fun calcularDescuentos() {
        val nombre = etNombre.text.toString().trim()
        if (nombre.isEmpty()) {
            etNombre.error = "Ingrese el nombre del empleado"
            return
        }

        val salarioStr = etSalario.text.toString().trim()
        val salarioBase = salarioStr.toDoubleOrNull()
        if (salarioBase == null || salarioBase < 0) {
            etSalario.error = "Ingrese un salario válido"
            return
        }

        // Cálculo de Renta (según tabla)
        val renta = calcularRenta(salarioBase)

        // AFP = 7.25% del salario
        val afp = salarioBase * 0.0725

        // ISSS = 3% del salario
        val isss = salarioBase * 0.03

        // Salario Neto
        val salarioNeto = salarioBase - renta - afp - isss

        // Formateamos los resultados
        val resultado = """
            Empleado: $nombre
            Renta: $${"%.2f".format(renta)}
            AFP (7.25%): $${"%.2f".format(afp)}
            ISSS (3%): $${"%.2f".format(isss)}
            ------------------------
            Salario Neto: $${"%.2f".format(salarioNeto)}
        """.trimIndent()

        tvResultado.text = resultado
    }

    /**
     * Cálculo de Renta según tramos:
     * Tramo 1: Hasta 472.00        => 0%
     * Tramo 2: 472.01 - 895.24     => 10% de (exceso de 472) + 17.67
     * Tramo 3: 895.25 - 2038.10    => 20% de (exceso de 895.24) + 60
     * Tramo 4: > 2038.10           => 30% de (exceso de 2038.10) + 288.57
     */
    private fun calcularRenta(salario: Double): Double {
        return when {
            salario <= 472.0 -> 0.0
            salario <= 895.24 -> (salario - 472) * 0.10 + 17.67
            salario <= 2038.10 -> (salario - 895.24) * 0.20 + 60
            else -> (salario - 2038.10) * 0.30 + 288.57
        }
    }
}