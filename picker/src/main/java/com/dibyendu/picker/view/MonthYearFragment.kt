package com.dibyendu.picker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dibyendu.picker.R
import com.dibyendu.picker.databinding.FragmentMonthYearPickerBinding
import com.dibyendu.picker.listener.ResultListener
import com.dibyendu.picker.model.Item
import com.dibyendu.picker.util.PickerUtils
import com.dibyendu.picker.view.adapter.MonthYearPickerAdapter
import java.util.*

internal class MonthYearFragment : Fragment() {
    internal var tabPosition = 0
    private lateinit var listener: ResultListener
    private lateinit var monthYearPickerAdapter: MonthYearPickerAdapter
    private lateinit var calendar: Calendar
    private lateinit var binding: FragmentMonthYearPickerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listener = requireArguments().getParcelable(PickerUtils.RESULT_TAG)!!
        binding = FragmentMonthYearPickerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.set(Calendar.YEAR, requireArguments().getInt(PickerUtils.YEAR_TAG))
        calendar.set(Calendar.MONTH, requireArguments().getInt(PickerUtils.MONTH_TAG))
        val dataSet = PickerUtils.getItems(tabPosition, calendar)
        monthYearPickerAdapter =
            MonthYearPickerAdapter(
                dataSet,
                tabPosition,
                object :
                    MonthYearPickerAdapter.OnChipSelectListener {
                    override fun onChipSelected(item: Item, tabPosition: Int) {
                        dataSet.forEach { it.isSelected = false }
                        item.isSelected = true
                        monthYearPickerAdapter.setData(dataSet)
                        monthYearPickerAdapter.notifyDataSetChanged()
                        listener.onSetResult(item)
                    }
                }
            )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(
                MonthYearPickerDialog.MarginItemDecoration(
                    resources.getDimension(R.dimen.item_space).toInt()
                )
            )
            adapter = monthYearPickerAdapter
            scrollToPosition(getScrollPosition())
        }
    }

    private fun getScrollPosition(): Int {
        val dataSet = PickerUtils.getItems(tabPosition, calendar)
        return if (tabPosition == 0) {
            calendar.get(Calendar.MONTH)
        } else {
            dataSet.filter { it.name == calendar.get(Calendar.YEAR).toString() }
                .map { dataSet.indexOf(it) }[0]
        }
    }

    internal companion object {
        fun newInstance(listener: ResultListener, calendar: Calendar): MonthYearFragment {
            val bundle = Bundle()
            bundle.putParcelable(PickerUtils.RESULT_TAG, listener)
            bundle.putInt(PickerUtils.MONTH_TAG, calendar.get(Calendar.MONTH))
            bundle.putInt(PickerUtils.YEAR_TAG, calendar.get(Calendar.YEAR))
            val fragment = MonthYearFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
