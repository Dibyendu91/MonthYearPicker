package com.dibyendu.picker.view

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dibyendu.picker.databinding.DialogMonthYearChooserBinding
import com.dibyendu.picker.listener.PickerListener
import com.dibyendu.picker.listener.ResultListener
import com.dibyendu.picker.model.Item
import com.dibyendu.picker.util.PickerUtils
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.dialog_month_year_chooser.*
import java.util.*

class MonthYearPickerDialog : DialogFragment() {

    private lateinit var binding: DialogMonthYearChooserBinding
    private lateinit var listener: PickerListener
    private var tabPosition = 0
    private lateinit var calendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            listener = requireArguments().getParcelable(PickerUtils.RESULT_TAG)!!
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            binding = DialogMonthYearChooserBinding.inflate(inflater)
            builder.setView(binding.root)
            binding.button.setOnClickListener {
                listener.onSetResult(calendar)
                dismiss()
            }
            binding.button2.setOnClickListener {
                dismiss()
            }
            calendar = Calendar.getInstance(Locale.ENGLISH)
            binding.viewPager.adapter = ScreenSlidePagerAdapter(this)
            calendar.set(Calendar.YEAR, requireArguments().getInt(PickerUtils.YEAR_TAG))
            calendar.set(Calendar.MONTH, requireArguments().getInt(PickerUtils.MONTH_TAG))
            tabPosition = binding.tabLayout.selectedTabPosition
            showTabItem(calendar, tabPosition)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    internal class MarginItemDecoration(private val spaceHeight: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            with(outRect) {
                top = spaceHeight
                left = spaceHeight
                right = spaceHeight
                bottom = spaceHeight
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount() = 2
        override fun createFragment(position: Int): Fragment {
            val fragment = MonthYearFragment.newInstance(object :
                ResultListener {
                override fun <T> onSetResult(item: T) {
                    var month: String? = null
                    var year: String? = null
                    when (item) {
                        is Item.Month -> {
                            month = item.name
                            tabPosition = 1
                        }
                        is Item.Year -> {
                            year = item.name
                        }
                    }
                    if (!month.isNullOrEmpty()) {
                        calendar[Calendar.MONTH] = PickerUtils.getMonthIndex(month)
                    }
                    if (!year.isNullOrEmpty()) {
                        calendar[Calendar.YEAR] = year.toInt()
                    }
                    showTabItem(calendar, tabPosition)
                }
            }, calendar)
            fragment.tabPosition = position
            return fragment
        }

    }

    private fun showTabItem(calendar: Calendar, tabPosition: Int) {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = PickerUtils.getMonth(calendar)
                1 -> tab.text = PickerUtils.getYear(calendar)
            }
        }.attach()
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(tabPosition))
    }

    companion object {
        private fun newInstance(
            listener: PickerListener,
            calendar: Calendar
        ): MonthYearPickerDialog {
            val bundle = Bundle()
            bundle.putParcelable(PickerUtils.RESULT_TAG, listener)
            bundle.putInt(PickerUtils.MONTH_TAG, calendar.get(Calendar.MONTH))
            bundle.putInt(PickerUtils.YEAR_TAG, calendar.get(Calendar.YEAR))
            val fragment = MonthYearPickerDialog()
            fragment.arguments = bundle
            return fragment
        }

        fun show(
            context: FragmentActivity,
            calendar: Calendar = Calendar.getInstance(),
            listener: PickerListener
        ) {
            newInstance(listener, calendar).show(
                context.supportFragmentManager,
                MonthYearPickerDialog::class.java.simpleName
            )
        }
    }

}