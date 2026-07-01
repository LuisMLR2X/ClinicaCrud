package com.example.clinicacrud.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//El codigo es la PK y no se autogenera: no debe repetirse
@Entity(tableName = "Doctor")
data class DoctorEntity(
    @PrimaryKey
    val codigo: String,
    val nombres: String,
    val apellidos: String,
    val sexo: String,
    val fechaNacimiento: String,
    val especialidadId: Long
)