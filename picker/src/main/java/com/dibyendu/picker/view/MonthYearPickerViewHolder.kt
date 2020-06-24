package com.dibyendu.picker.view

import androidx.recyclerview.widget.RecyclerView
import com.dibyendu.picker.databinding.ItemChipBinding
import com.dibyendu.picker.model.Item
import com.dibyendu.picker.view.adapter.MonthYearPickerAdapter

internal class MonthYearPickerViewHolder(private val binding: ItemChipBinding) :
    RecyclerView.ViewHolder(binding.root) {
    internal fun bindData(
        item: Item,
        listener: MonthYearPickerAdapter.OnChipSelectListener,
        tabPosition: Int
    ) {
        binding.chip.text = item.name
        binding.chip.isChecked = item.isSelected
        binding.chip.setOnClickListener { listener.onChipSelected(item, tabPosition) }
    }
}