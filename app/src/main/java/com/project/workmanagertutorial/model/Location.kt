package com.project.workmanagertutorial.model

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Long,
    val coordinates: Coordinates,
    val timezone: Timezone,
)