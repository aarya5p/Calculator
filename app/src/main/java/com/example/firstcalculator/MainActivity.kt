package com.example.firstcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var enteredTextView: TextView
    lateinit var ResultTextView: TextView

    var currentInput: String = ""
    var currentOperator: String? = null
    var firstOperand: Double = 0.0
    var isResultDisplayed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enteredTextView = findViewById(R.id.textView2)
        ResultTextView = findViewById(R.id.result)

        val buttons = arrayOf(
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine,
            R.id.ZeroZero,
            R.id.zero,
            R.id.dot,

            )
        for (buttonID in buttons) {
            findViewById<Button>(buttonID).setOnClickListener { onDigitClicked(it) }

        }
        findViewById<Button>(R.id.plus).setOnClickListener { onOperatorClicked("+") }
        findViewById<Button>(R.id.Minus).setOnClickListener { onOperatorClicked("-") }
        findViewById<Button>(R.id.Multiply).setOnClickListener { onOperatorClicked("*") }
        findViewById<Button>(R.id.Divide).setOnClickListener { onOperatorClicked("/") }
        findViewById<Button>(R.id.Modulo).setOnClickListener { onOperatorClicked("%") }


        findViewById<Button>(R.id.Clear).setOnClickListener { backspace() }
        findViewById<Button>(R.id.AllClear).setOnClickListener { ClearAll() }
        findViewById<Button>(R.id.result).setOnClickListener { calculateResult() }


    }

    fun updateDisplay() {
        val expression = if (currentOperator != null) {
            "$firstOperand $currentOperator $currentInput"
        } else {
            currentInput
        }
        enteredTextView.text = expression
        ResultTextView.text = if (currentOperator != null) {
            "$firstOperand $currentOperator"
        } else {
            ""
        }
    }

    fun onDigitClicked(view: View) {
        if (isResultDisplayed) {
            ClearAll()
            isResultDisplayed = false
        }
        currentInput += (view as Button).text.toString()
    }

    fun onOperatorClicked(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator != null) {
                calculateResult()
            }
            currentOperator = operator
            firstOperand = currentInput.toDouble()
            currentInput = ""
            isResultDisplayed = false
            updateDisplay()


        }


    }

    fun ClearAll() {
        currentInput = ""
        currentOperator = null
        firstOperand = 0.0
        isResultDisplayed = false
        updateDisplay()

    }

    fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
        }
    }

    fun calculateResult() {
        if (currentOperator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            when (currentOperator) {
                "+" -> firstOperand += secondOperand
                "-" -> firstOperand -= secondOperand
                "*" -> firstOperand *= secondOperand
                "/" -> {
                    if (secondOperand != 0.0) {
                        firstOperand /= secondOperand
                    }
                }

                "%" -> firstOperand = (firstOperand * secondOperand) / 100

            }
            currentOperator = null
            currentInput = firstOperand.toString()
            isResultDisplayed = true
            updateDisplay()
            ResultTextView.text = "=$firstOperand"

        }
    }
}

