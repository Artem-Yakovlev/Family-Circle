package com.tydeya.familycircle.utils.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.popBackStack() {
    NavHostFragment.findNavController(this).popBackStack()
}