package edd_ie.com.github.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edd_ie.com.github.calculator.databinding.ActivityMainBinding
import java.util.LinkedList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var num: String? = null
    private var display: String? = null
    private var result: Double = 0.0
    private var prevResult: Double = 0.0

    private var operators = LinkedList<Char>()
    private var numbers = LinkedList<Double>()

    private var operating: Boolean = false

//    var lastNode = numbers.last
//    lastNode?.value = 5.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.result.text = "0"

        binding.btn0.setOnClickListener { onNumClick("0") }
        binding.btn1.setOnClickListener { onNumClick("1") }
        binding.btn2.setOnClickListener { onNumClick("2") }
        binding.btn3.setOnClickListener { onNumClick("3") }
        binding.btn4.setOnClickListener { onNumClick("4") }
        binding.btn5.setOnClickListener { onNumClick("5") }
        binding.btn6.setOnClickListener { onNumClick("6") }
        binding.btn7.setOnClickListener { onNumClick("7") }
        binding.btn8.setOnClickListener { onNumClick("8") }
        binding.btn9.setOnClickListener { onNumClick("9") }
        binding.btnDot.setOnClickListener {onNumClick(".")}


        binding.btnAC.setOnClickListener {clear()}
        binding.btnAdd.setOnClickListener {operation('+')}
        binding.btnMin.setOnClickListener {operation('-')}
        binding.btnDiv.setOnClickListener {operation('/')}
        binding.btnMulti.setOnClickListener {operation('*')}
        binding.btnDel.setOnClickListener {del()}
        binding.btnEqual.setOnClickListener {equal()}


    }

    private fun onNumClick(clickedNum: String){
        if(num == null && clickedNum != "."){
            num = clickedNum
            display = clickedNum
            prevResult = 0.0
            operating = true
        }
        else{
            num += clickedNum
            display += clickedNum
        }

        binding.history.text = display
    }

    private fun equal(){
        if(operating){
            if(display!!.last()!=' ' && display!!.isNotEmpty()){
                if(operators.isEmpty()){
                    result = num!!.toDouble()
                    binding.result.text = result.toString()
                }
                else{
                    numbers.add(num!!.toDouble())
                    when(operators.last){
                        '+' -> add()
                        '-' -> sub()
                        '/' -> div()
                        '*' -> multi()
                    }
                    prevResult = result
                    clear()
                    operating = true
                    binding.result.text = prevResult.toString()
                }
            }
            else if(display!!.last()==' '){
                Toast.makeText(this, "Syntax Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun del(){
        if(operating){
            // Delete to clear memory
            if (prevResult != 0.0 && display == null){
                clear()
                prevResult = 0.0
            }
            //Delete a number
            else if(display!!.last()!=' '){
                if(display!!.length>1){
                    num = num!!.dropLast(1)
                    display = display!!.dropLast(1)
                    binding.history.text = display
                }else{
                    clear()
                }
            }
            //Delete an operator
            else{
                display = display!!.dropLast(3)
                binding.history.text = display

                when (operators.removeLast()) {
                    '+' -> add()
                    '-' -> sub()
                    '/' -> div()
                    '*' -> multi()
                }
                num = numbers.removeLast().toString()

                binding.result.text = result.toString()

            }

        }
    }

    private fun clear(){
        binding.result.text = "0"
        binding.history.text = ""

        num = null
        display = null
        result = 0.0

        operators.clear()
        numbers.clear()
        operating = false
    }

    @SuppressLint("SetTextI18n")
    private fun operation(operation: Char){
        if(operating){
            if (display != null && prevResult == 0.0) {
                if (numbers.isEmpty()) {
                    numbers.add(num!!.toDouble())
                    result = num!!.toDouble()
                    operators.add(operation)
                } else {
                    numbers.add(num!!.toDouble())
                    when (operators.last) {
                        '+' -> add()
                        '-' -> sub()
                        '/' -> div()
                        '*' -> multi()
                    }

                    operators.add(operation)
                }

            } else if (prevResult != 0.0 && display == null) {
                numbers.add(prevResult)
                result = prevResult
                operators.add(operation)
                prevResult = 0.0
                display = "$result"
            }

            num = ""
            display += " $operation "
            binding.history.text = display
            binding.result.text = result.toString()
        }
    }

    private fun add(){
        result += numbers.last
    }

    private fun sub(){
        result -= numbers.last
    }

    private fun div(){
        result /= numbers.last
    }

    private fun multi(){
        result *= numbers.last
    }

}