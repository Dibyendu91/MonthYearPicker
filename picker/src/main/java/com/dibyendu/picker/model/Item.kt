package com.dibyendu.picker.model

internal sealed class Item(val name: String, var isSelected: Boolean = false) {
    class Month(name: String, isSelected: Boolean = false) : Item(name, isSelected)
    class Year(name: String, isSelected: Boolean = false) : Item(name, isSelected)
}