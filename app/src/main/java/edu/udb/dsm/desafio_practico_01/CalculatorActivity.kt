package edu.udb.dsm.desafio_practico_01

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import kotlin.math.pow

class CalculatorActivity : AppCompatActivity() {
    private var resultOut: TextView? = null
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var operator = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calculator_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultOut = findViewById(R.id.cal_result)

        //
        val operand1In: EditText = findViewById(R.id.cal_operand1)
        val operand2In: EditText = findViewById(R.id.cal_operand2)
        operand1In.doAfterTextChanged { text ->
            val value = text.toString().toDoubleOrNull()
            if (value === null) {
                Toast.makeText(this, R.string.cal_no_operand1, Toast.LENGTH_SHORT).show()
            } else {
                operand1 = value
                operate()
            }
        }
        operand2In.doAfterTextChanged { text ->
            val value = text.toString().toDoubleOrNull()
            if (value === null) {
                Toast.makeText(this, R.string.cal_no_operand2, Toast.LENGTH_SHORT).show()
            } else {
                operand2 = value
                operate()
            }
        }

        //
        val operatorIn: Spinner = findViewById(R.id.cal_operator)
        ArrayAdapter.createFromResource(
            this, R.array.cal_operators, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            operatorIn.adapter = adapter
        }
        operatorIn.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                operator = position
                operate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                operator = -1
                operate()
            }
        }
    }

    private fun operate() {
        if (operator == 3 && operand2 == 0.0) {
            resultOut?.text = resources.getString(R.string.cal_0division)
        }

        val result = when (operator) {
            0 -> operand1.plus(operand2)
            1 -> operand1.minus(operand2)
            2 -> operand1.times(operand2)
            3 -> operand1.div(operand2)
            4 -> operand1.pow(operand2)
            5 -> operand1.pow(1 / operand2)
            else -> 0.0
        }
        resultOut?.text = result.toBigDecimal().toPlainString()
    }
}