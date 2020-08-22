package com.example.alarmz.stopwatch

data class Lap(
    var lapNo: Int,
    var elapsedTime: Long,
    var timeDifference: Long
) {
}