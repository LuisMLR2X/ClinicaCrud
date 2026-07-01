package com.example.clinicacrud.domain.model

data class Cita(
    val id: Long = 0,
    val doctorCodigo: String,
    val pacienteId: Long,
    val fecha: String,
    val hora: String,
    val motivoConsulta: String,
    val diagnostico: String,
    val costo: Double,
    val estado: String
)