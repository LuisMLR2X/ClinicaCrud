package com.example.clinicacrud.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//Convertimos la data class en una entidad o tabla
//para poder almacenar en la BD con el nombre: Especialidad
@Entity(tableName = "Especialidad")
data class EspecialidadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val descripcion: String
)