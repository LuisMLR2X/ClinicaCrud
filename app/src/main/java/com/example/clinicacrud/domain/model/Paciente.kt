package com.example.clinicacrud.domain.model

data class Paciente(
    val id: Long = 0,
    val dni: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String
)