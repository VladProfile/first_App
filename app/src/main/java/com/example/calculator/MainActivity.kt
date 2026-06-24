package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var display: TextView? = null
    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation = ""
    private var isNewNumber = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        display = binding.display

        setupNumberButtons()
        setupOperationButtons()
        setupSpecialButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9, binding.btnDot
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                onNumberClick(button.text.toString())
            }
        }
    }

    private fun setupOperationButtons() {
        binding.btnAdd.setOnClickListener { onOperationClick("+") }
        binding.btnSubtract.setOnClickListener { onOperationClick("-") }
        binding.btnMultiply.setOnClickListener { onOperationClick("*") }
        binding.btnDivide.setOnClickListener { onOperationClick("/") }
    }

    private fun setupSpecialButtons() {
        binding.btnEquals.setOnClickListener { onEqualsClick() }
        binding.btnClear.setOnClickListener { onClearClick() }
    }

    private fun onNumberClick(number: String) {
        val currentText = display?.text.toString()

        if (isNewNumber) {
            display?.text = number
            isNewNumber = false
        } else {
            if (number == "." && currentText.contains(".")) {
                return
            }
            display?.text = currentText + number
        }
    }

    private fun onOperationClick(op: String) {
        val currentText = display?.text.toString()
        if (currentText.isNotEmpty()) {
            firstNumber = currentText.toDouble()
            operation = op
            isNewNumber = true
        }
    }

    private fun onEqualsClick() {
        val currentText = display?.text.toString()
        if (currentText.isNotEmpty() && operation.isNotEmpty()) {
            secondNumber = currentText.toDouble()
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else 0.0
                else -> 0.0
            }
            display?.text = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                String.format("%.2f", result)
            }
            operation = ""
            isNewNumber = true
        }
    }

    private fun onClearClick() {
        display?.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
        operation = ""
        isNewNumber = true
    }
}
