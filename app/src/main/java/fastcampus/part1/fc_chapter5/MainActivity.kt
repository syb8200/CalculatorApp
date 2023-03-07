package fastcampus.part1.fc_chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import fastcampus.part1.fc_chapter5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    // xml에서 접근할 수 있어야하기 때문에 -> public
    fun numberClicked(view: View) {
        val numberString =
            (view as? Button)?.text?.toString() ?: "" // view의 모든 요소가 Button이 아닐 수도 있기 때문에 as? 사용
        val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText

        numberText.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view: View) {
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()

        updateEquationTextView()
        binding.resultTextView.text = ""
    }

    fun equalClicked(view: View) {
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식 입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val firstNumber = firstNumberText.toString().toInt()
        val secondNumber = secondNumberText.toString().toInt()

        val result = when(operatorText.toString()) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            else -> "잘못된 수식 입니다."
        }.toString()

        binding.resultTextView.text = result
    }

    fun operatorClicked(view: View) {
        val operatorString = (view as? Button)?.text?.toString() ?: ""

        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "먼저 숫자를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (secondNumberText.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString)
        updateEquationTextView()
    }

    private fun updateEquationTextView() {
        binding.equationTextView.text = "$firstNumberText $operatorText $secondNumberText"
    }
}