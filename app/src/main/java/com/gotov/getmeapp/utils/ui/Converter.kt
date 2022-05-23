package com.gotov.getmeapp.utils.ui

import android.content.res.Resources
import android.util.TypedValue

fun Float.toDips(res: Resources) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, res.displayMetrics).toInt()