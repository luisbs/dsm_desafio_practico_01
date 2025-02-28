package edu.udb.dsm.desafio_practico_01

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioButton
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

        // operands
        findViewById<EditText>(R.id.cal_operand1).doAfterTextChanged { text ->
            val value = text.toString().toDoubleOrNull()
            if (value === null) {
                Toast.makeText(this, R.string.cal_no_operand1, Toast.LENGTH_SHORT).show()
            } else {
                operand1 = value
                operate()
            }
        }
        findViewById<EditText>(R.id.cal_operand2).doAfterTextChanged { text ->
            val value = text.toString().toDoubleOrNull()
            if (value === null) {
                Toast.makeText(this, R.string.cal_no_operand2, Toast.LENGTH_SHORT).show()
            } else {
                operand2 = value
                operate()
            }
        }

        findViewById<RadioButton>(R.id.cal_add).setOnCheckedChangeListener(this::change)
        findViewById<RadioButton>(R.id.cal_sub).setOnCheckedChangeListener(this::change)
        findViewById<RadioButton>(R.id.cal_mul).setOnCheckedChangeListener(this::change)
        findViewById<RadioButton>(R.id.cal_div).setOnCheckedChangeListener(this::change)
        findViewById<RadioButton>(R.id.cal_pow).setOnCheckedChangeListener(this::change)
        findViewById<RadioButton>(R.id.cal_sqrt).setOnCheckedChangeListener(this::change)
    }

    // operators
    private fun change(view: CompoundButton, isChecked: Boolean) {
        if (view.id == operator) return
        findViewById<RadioButton>(operator)?.isChecked = false
        operator = view.id
        operate()
    }

    private fun operate() {
        if (operator == R.id.cal_div && operand2 == 0.0) {
            resultOut?.text = resources.getString(R.string.cal_0division)
            return
        }
        if (operator == R.id.cal_sqrt && operand2 <= 0) {
            resultOut?.text = resources.getString(R.string.cal_negative_root)
            return
        }

        val result = when (operator) {
            R.id.cal_add -> operand1.plus(operand2)
            R.id.cal_sub -> operand1.minus(operand2)
            R.id.cal_mul -> operand1.times(operand2)
            R.id.cal_div -> operand1.div(operand2)
            R.id.cal_pow -> operand1.pow(operand2)
            R.id.cal_sqrt -> operand1.pow(1 / operand2)
            else -> 0.0
        }
        resultOut?.text = result.toBigDecimal().toPlainString()
    }
}