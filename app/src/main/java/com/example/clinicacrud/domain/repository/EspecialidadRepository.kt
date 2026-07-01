// domain/repository/EspecialidadRepository.kt
package com.example.clinicacrud.domain.repository

import com.example.clinicacrud.domain.model.Especialidad

interface EspecialidadRepository {
    fun getEspecialidades(): List<Especialidad>
    fun insertEspecialidad(especialidad: Especialidad)
    fun updateEspecialidad(especialidad: Especialidad)
    fun deleteEspecialidad(especialidad: Especialidad)
}