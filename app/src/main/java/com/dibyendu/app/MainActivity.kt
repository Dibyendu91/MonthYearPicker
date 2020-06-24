package com.dibyendu.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dibyendu.app.databinding.ActivityMainBinding
import com.dibyendu.picker.listener.PickerListener
import com.dibyendu.picker.util.PickerUtils
import com.dibyendu.picker.view.MonthYearPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.name =
            PickerUtils.getMonthYearDisplay(this, Calendar.getInstance(), PickerUtils.Format.SHORT)
        button.setOnClickListener {
            MonthYearPickerDialog.show(this, listener = object : PickerListener {
                override fun onSetResult(calendar: Calendar) {
                    binding.name = PickerUtils.getMonthYearDisplay(
                        this@MainActivity,
                        calendar,
                        PickerUtils.Format.SHORT
                    )
                }
            })
        }
    }

}
