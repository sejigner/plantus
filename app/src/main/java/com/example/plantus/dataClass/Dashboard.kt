package com.example.plantus.dataClass

data class Dashboard(
    val Humidity_value: String,
    val Lux_value: String,
    val Soil_value: String,
    val Temperature_value: String,
    val timestamp: Long,
) {
    constructor() : this ("0","0","0","25",0)
}
