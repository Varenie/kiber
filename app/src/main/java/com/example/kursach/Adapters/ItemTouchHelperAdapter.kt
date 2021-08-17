package com.example.kursach.Adapters

interface ItemTouchHelperAdapter { //интерфекйс для swipe и drag&drop

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}