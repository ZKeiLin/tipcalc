package edu.washington.zlin2016.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // univeral
    private var amountStr = ""
    private var amount = 0
    private var tipAmount = 15


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate event fired")

        val tipButton = findViewById<Button>(R.id.tipButton)
        val inputAmt = findViewById<EditText>(R.id.inputAmt)

        tipButton.isEnabled = false

        inputAmt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.toString() != amount.toString()){
                    inputAmt.removeTextChangedListener(this)
                    val pureNum = s.toString().replace("[$,.]".toRegex(), "");
                    amount = if (pureNum.toIntOrNull() == null) 0 else pureNum.toInt()
                    amountStr = "$%.2f".format(amount * 0.01)
                    inputAmt.setText(amountStr);
                    inputAmt.setSelection(amountStr.length);
                    inputAmt.addTextChangedListener(this)

                    tipButton.isEnabled = amount > 0
                }
            }
        })

        tipButton.setOnClickListener {
            Toast.makeText(applicationContext, "$%.2f".format(amount * (tipAmount * 0.01) * 0.01), Toast.LENGTH_LONG).show()
            Log.i(TAG, "button clicked!")
        }


        // Spinner
        val tipSelector = findViewById<Spinner>(R.id.tipSelector)
        val tipOptions = arrayOf("10%", "15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipSelector!!.setAdapter(adapter)



        tipSelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                tipAmount = tipOptions.get(position).replace("%", "").toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        tipSelector.setSelection(1)
    }

}
