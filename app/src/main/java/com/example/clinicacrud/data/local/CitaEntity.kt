package com.example.clinicacrud.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cita")
data class CitaEntity(
    @PrimaryKey(autoGenerate = true)
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