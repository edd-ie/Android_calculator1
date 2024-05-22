package edd_ie.com.github.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edd_ie.com.github.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var num: String? = null

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

    }

    fun onNumClick(clickedNum: String){
        if(num == null){
            num = clickedNum
        }
        else{
            num += clickedNum
        }

        binding.result.text = num
    }
}