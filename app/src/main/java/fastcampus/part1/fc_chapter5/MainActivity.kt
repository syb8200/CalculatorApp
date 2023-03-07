package fastcampus.part1.fc_chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import fastcampus.part1.fc_chapter5.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")

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
        binding.equationTextView.text = "0"  // 기존 equation 텍스트 -> 0으로 대체
        binding.resultTextView.text = ""
    }

    fun equalClicked(view: View) {
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식 입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val firstNumber = firstNumberText.toString().toBigDecimal()  // 소수점을 정확하게 계산하기 위해서 BigDecimal 사용해야 한다.
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when (operatorText.toString()) {
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber - secondNumber)
            else -> "잘못된 수식 입니다."
        }

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
        // decimalFormat에서 포멧팅 진행 : 3자리 마다 ,찍힘 + 0543 이런 숫자는 없기 때문에 맨 앞에 0 입력해하면 0 사라짐
        // 이렇게 포멧팅 한 결과 값 == 문자열
        val firstFormattedNumber = if (firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if (secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""

        // 값을 처음 입력했을 때 UI 상 공백 추가되는 것 막기
        if (operatorText.isEmpty()) {
            binding.equationTextView.text = "$firstFormattedNumber"
        } else {
            binding.equationTextView.text =
                "$firstFormattedNumber $operatorText $secondFormattedNumber"
        }
    }
}