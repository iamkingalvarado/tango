package io.tango.challenge.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, function: (T) -> Unit) {
    liveData.observe(this, { it?.let { function(it) } })
}