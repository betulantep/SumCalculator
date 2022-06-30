package com.betulantep.sumcalculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.betulantep.sumcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var numbersList = ArrayList<Button>()
    private var addNumbersText = "" // görsel güzel gözükmesi için örn: 85 + 25 + 40 gibi
    private var sumResult = 0 // toplam sonucunu tutuyor
    private var inputNumber = "" // artıya basılmadan önce girilen sayıyı tutuyor
    private var clickEquals = false // + ya basıp sayı girmeden eşittire basılmasına önlem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numbersListAdd()
        keyboard()
        binding.apply {
            buttonPlus.setOnClickListener { buttonPlusClick() }
            buttonEquals.setOnClickListener { buttonEqualsClick() }
            buttonDelete.setOnClickListener { resultDelete() }
        }
    }

    private fun buttonEqualsClick() {
        if (inputNumber == "") {//arka arkaya eşittire basılmasına önlem
            showToast(this,"Sayı giriniz")
        } else {
            clickEquals = true
            sumResult += inputNumber.toInt()
            binding.tvResult.text = sumResult.toString()
            inputNumber = ""
            addNumbersText = ""
        }
    }


    private fun buttonPlusClick() {
        if (clickEquals) { // eşittire bastıktan sonra toplam sonuçla devam edebilmesi için
            clickEquals = false
            binding.tvResult.text = ""
            addNumbersText = "$sumResult + "
            binding.tvSubTotal.text = addNumbersText
        } else {
            //artı dan sonra sayı girmeden eşittire basılmasına önlem
            if (binding.tvSubTotal.text != "" && inputNumber != "") {
                sumResult += inputNumber.toInt()
                addNumbersText += " + "
                binding.tvSubTotal.text = addNumbersText
                inputNumber = ""
            } else {
                showToast(this,"Sayı giriniz")
            }
        }
    }
    private fun keyboard() { //rakamların yan yana yazılması
            for (num in numbersList) {
                num.setOnClickListener {
                    if(!clickEquals){//eşittirden sonra artıya basmadan sayı girilmesini önleme
                        inputNumber += num.text  // artıya basılmadan önce girilen sayıyı tutuyor
                        addNumbersText += num.text //örn: 85 + 25 + 40 düzgün görsel için
                        binding.tvSubTotal.text = addNumbersText
                    }else{
                        showToast(this,"Toplama işaretine basınız")
                    }
                }
            }
    }

    private fun numbersListAdd() {
        for (i in 0..9) {
            val buttonId = "button$i"
            val buttonResId = resources.getIdentifier(buttonId, "id", packageName)
            with(binding) {
                numbersList.add(root.findViewById(buttonResId))
            }
        }
    }

    private fun resultDelete() {
        binding.tvResult.text = ""
        binding.tvSubTotal.text = ""
        addNumbersText = ""
        inputNumber = ""
        sumResult = 0
        clickEquals = false
    }
}