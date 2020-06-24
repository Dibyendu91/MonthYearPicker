package com.dibyendu.picker.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dibyendu.picker.listener.PickerListener
import com.dibyendu.picker.sample.databinding.ActivityPickerViewBinding
import com.dibyendu.picker.util.PickerUtils
import com.dibyendu.picker.view.MonthYearPickerDialog
import java.util.*

class PickerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPickerViewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_picker_view)
        var mCalendar = Calendar.getInstance()
        binding.date =
            PickerUtils.getMonthYearDisplay(this, mCalendar, PickerUtils.Format.SHORT)
        binding.button.setOnClickListener {
            MonthYearPickerDialog.show(context = this, calendar = mCalendar, listener = object : PickerListener {
                override fun onSetResult(calendar: Calendar) {
                    mCalendar = calendar
                    binding.date = PickerUtils.getMonthYearDisplay(
                        this@PickerViewActivity,
                        calendar,
                        PickerUtils.Format.SHORT
                    )
                }
            })
        }
    }
}
