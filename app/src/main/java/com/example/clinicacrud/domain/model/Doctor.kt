package com.example.clinicacrud.domain.model

data class Doctor(
    val codigo: String,
    val nombres: String,
    val apellidos: String,
    val sexo: String,
    val fechaNacimiento: String,
    val especialidadId: Long
)