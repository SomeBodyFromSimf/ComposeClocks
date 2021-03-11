package com.example.composeclocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeclocks.clock.TimePosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {

    private val cityTimeState = mutableStateOf(listOf(
        Triple(12,20,43) to "Pekin",
        Triple(14,24,43) to "Moscow",
        Triple(16,23,43) to "Paris",
        Triple(21,50,43) to "London",

    ))
    val cityTimes: State<List<Pair<TimePosition, String>>> = cityTimeState

    init {
        startCheckTime()
    }

    private fun startCheckTime() {
        viewModelScope.launch {
            while (true) {
                cityTimeState.value = cityTimeState.value.map {
                    val calendar: Calendar = Calendar.getInstance(getLocaleByStr(it.second))
                    Triple(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND)) to it.second
                }
                delay(200)
            }
        }
    }

    private fun getLocaleByStr(str: String): TimeZone = when (str){
        "Pekin" -> TimeZone.getTimeZone("GMT+8:00")
        "Paris" -> TimeZone.getTimeZone("GMT+1:00")
        "London" -> TimeZone.getTimeZone("GMT")
        else -> TimeZone.getTimeZone("GMT+3:00")
    }
}