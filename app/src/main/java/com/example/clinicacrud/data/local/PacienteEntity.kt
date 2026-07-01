package com.example.clinicacrud.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Paciente")
data class PacienteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dni: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String
)