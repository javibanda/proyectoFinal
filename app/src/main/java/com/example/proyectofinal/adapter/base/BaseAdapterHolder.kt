package com.example.proyectofinal.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

interface BaseAdapterHolder {

    @LayoutRes
    fun getViewHolderLayoutId(viewType: Int): Int

    fun createView(parent: ViewGroup, viewType: Int): View =
            LayoutInflater.from(parent.context)
                    .inflate(getViewHolderLayoutId(viewType), parent, false)
}