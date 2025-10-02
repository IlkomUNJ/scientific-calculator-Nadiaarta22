package com.example.tugas1_mokom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas1_mokom.ui.theme.Tugas1_MokomTheme
import kotlin.math.*   // sin, cos, tan, asin, acos, atan, pow, sqrt, log, ln, dll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tugas1_MokomTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var input by remember { mutableStateOf("0") }
    var isScientific by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header + switch mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isScientific) "Scientific Mode" else "Basic Mode",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Switch(
                checked = isScientific,
                onCheckedChange = { isScientific = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFFF9800)
                )
            )
        }

        // layar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = input,
                fontSize = 48.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(16.dp)
            )
        }

        // keyboard
        if (isScientific) {
            ScientificKeyboard(input) { input = it }
        } else {
            BasicKeyboard(input) { input = it }
        }
    }
}

@Composable
fun BasicKeyboard(input: String, onInputChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalcButton("AC", Color.LightGray, Modifier.weight(1f)) { onInputChange("0") }
            CalcButton("+/-", Color.LightGray, Modifier.weight(1f)) { onInputChange(toggleSign(input)) }
            CalcButton("%", Color.LightGray, Modifier.weight(1f)) { onInputChange(if (input == "0") "0" else "$input%") }
            CalcButton("÷", Color(0xFFFF9800), Modifier.weight(1f)) { onInputChange(if (input == "0") "0" else "$input/") }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("7", "8", "9").forEach { v ->
                CalcButton(v, Color.DarkGray, Modifier.weight(1f)) {
                    onInputChange(if (input == "0") v else "$input$v")
                }
            }
            CalcButton("×", Color(0xFFFF9800), Modifier.weight(1f)) { onInputChange(if (input == "0") "0" else "$input*") }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("4", "5", "6").forEach { v ->
                CalcButton(v, Color.DarkGray, Modifier.weight(1f)) {
                    onInputChange(if (input == "0") v else "$input$v")
                }
            }
            CalcButton("−", Color(0xFFFF9800), Modifier.weight(1f)) { onInputChange(if (input == "0") "0" else "$input-") }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf("1", "2", "3").forEach { v ->
                CalcButton(v, Color.DarkGray, Modifier.weight(1f)) {
                    onInputChange(if (input == "0") v else "$input$v")
                }
            }
            CalcButton("+", Color(0xFFFF9800), Modifier.weight(1f)) { onInputChange(if (input == "0") "0" else "$input+") }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalcButton("0", Color.DarkGray, Modifier.weight(2f), isCircle = false) { onInputChange(if (input == "0") "0" else "input") }
            CalcButton(".", Color.DarkGray, Modifier.weight(1f)) { onInputChange(if (input.contains(".")) input else "$input.") }
            CalcButton("=", Color(0xFFFF9800), Modifier.weight(1f)) {
                onInputChange(try { eval(input).toString() } catch (_: Exception) { "Error" })
            }
        }
    }
}

@Composable
fun ScientificKeyboard(input: String, onInputChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalcButton("sin", Color.Gray, Modifier.weight(1f)) { onInputChange("sin($input)") }
            CalcButton("cos", Color.Gray, Modifier.weight(1f)) { onInputChange("cos($input)") }
            CalcButton("tan", Color.Gray, Modifier.weight(1f)) { onInputChange("tan($input)") }
            CalcButton("√", Color.Gray, Modifier.weight(1f)) { onInputChange("sqrt($input)") }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalcButton("asin", Color.Gray, Modifier.weight(1f)) { onInputChange("asin($input)") }
            CalcButton("acos", Color.Gray, Modifier.weight(1f)) { onInputChange("acos($input)") }
            CalcButton("atan", Color.Gray, Modifier.weight(1f)) { onInputChange("atan($input)") }
            CalcButton("x!", Color.Gray, Modifier.weight(1f)) { onInputChange("${input}!") }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CalcButton("log", Color.Gray, Modifier.weight(1f)) { onInputChange("log10($input)") }
            CalcButton("ln", Color.Gray, Modifier.weight(1f)) { onInputChange("ln($input)") }
            CalcButton("^", Color.Gray, Modifier.weight(1f)) { onInputChange("$input^") }
            CalcButton("π", Color.Gray, Modifier.weight(1f)) { onInputChange(if (input == "0") PI.toString() else "$input${PI}") }
        }
        // angka/operator bawaan
        BasicKeyboard(input, onInputChange)
    }
}

@Composable
fun CalcButton(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    isCircle: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(if (isCircle) 1f else 2f),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = if (color == Color.LightGray) Color.Black else Color.White
        ),
        shape = if (isCircle) CircleShape else RoundedCornerShape(50)
    ) {
        Text(text, fontSize = 22.sp)
    }
}

fun toggleSign(expr: String): String {
    return try {
        val num = expr.toDouble()
        if (num != 0.0) (-num).toString() else "0"
    } catch (_: Exception) {
        expr
    }
}

// faktorial (dengan batas supaya tidak overflow)
fun factorial(n: Int): Long {
    if (n < 0) return 0
    if (n > 20) return Long.MAX_VALUE // biar tidak overflow
    var result = 1L
    for (i in 1..n) result *= i
    return result
}

fun eval(expr: String): Double {
    return try {
        when {
            expr.startsWith("sin(") -> sin(eval(expr.substringAfter("sin(").substringBeforeLast(")")))
            expr.startsWith("cos(") -> cos(eval(expr.substringAfter("cos(").substringBeforeLast(")")))
            expr.startsWith("tan(") -> tan(eval(expr.substringAfter("tan(").substringBeforeLast(")")))
            expr.startsWith("asin(") -> asin(eval(expr.substringAfter("asin(").substringBeforeLast(")")))
            expr.startsWith("acos(") -> acos(eval(expr.substringAfter("acos(").substringBeforeLast(")")))
            expr.startsWith("atan(") -> atan(eval(expr.substringAfter("atan(").substringBeforeLast(")")))
            expr.startsWith("sqrt(") -> sqrt(eval(expr.substringAfter("sqrt(").substringBeforeLast(")")))
            expr.startsWith("log10(") -> log10(eval(expr.substringAfter("log10(").substringBeforeLast(")")))
            expr.startsWith("ln(") -> ln(eval(expr.substringAfter("ln(").substringBeforeLast(")")))
            expr.endsWith("!") -> factorial(eval(expr.dropLast(1)).toInt()).toDouble()
            expr.contains("^") -> {
                val parts = expr.split("^")
                if (parts.size == 2) parts[0].toDouble().pow(parts[1].toDouble()) else Double.NaN
            }
            else -> {
                val operator = expr.find { it in "+-*/%" }
                if (operator != null) {
                    val parts = expr.split(operator)
                    if (parts.size == 2) {
                        val left = parts[0].toDouble()
                        val right = parts[1].toDouble()
                        when (operator) {
                            '+' -> left + right
                            '-' -> left - right
                            '*' -> left * right
                            '/' -> if (right != 0.0) left / right else Double.NaN
                            '%' -> left % right
                            else -> Double.NaN
                        }
                    } else expr.toDoubleOrNull() ?: Double.NaN
                } else expr.toDoubleOrNull() ?: Double.NaN
            }
        }
    } catch (_: Exception) {
        Double.NaN
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Tugas1_MokomTheme {
        CalculatorApp()
    }
}
