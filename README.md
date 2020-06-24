# Month-Year Picker
## About
![icon](screen_shots/ic-month-year-picker.png)
This is a library to open a picker with month and year view. You can pick any month and year from 
picker and you will receive a calender instance from where you can fetch selected month year.

## Preview
![app_view](screen_shots/month-year-picker.gif)

## Installation
Use the following step to use this library.

## Usage

```kotlin
import com.dibyendu.picker.util.PickerUtils
import com.dibyendu.picker.view.MonthYearPickerDialog
import com.dibyendu.picker.listener.PickerListener

MonthYearPickerDialog.show(context = this, calendar = calendar, listener = object : PickerListener {
                override fun onSetResult(calendar: Calendar) {
                    mCalendar = calendar
                    val date: String = PickerUtils.getMonthYearDisplay(
                        this@Activity,
                        calendar,
                        PickerUtils.Format.SHORT
                    )
                }
            })
```