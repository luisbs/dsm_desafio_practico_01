package edu.udb.dsm.desafio_practico_01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gradesBtn: Button = findViewById(R.id.main_grades_btn)
        val salaryBtn: Button = findViewById(R.id.main_salary_btn)
        val calculatorBtn: Button = findViewById(R.id.main_calculator_btn)

        gradesBtn.setOnClickListener { startActivity(Intent(this, GradesActivity::class.java)) }
        salaryBtn.setOnClickListener { startActivity(Intent(this, SalaryActivity::class.java)) }
        calculatorBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CalculatorActivity::class.java
                )
            )
        }
    }
}