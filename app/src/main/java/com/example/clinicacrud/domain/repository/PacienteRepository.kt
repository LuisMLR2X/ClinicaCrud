// domain/repository/PacienteRepository.kt
package com.example.clinicacrud.domain.repository

import com.example.clinicacrud.domain.model.Paciente

interface PacienteRepository {
    fun getPacientes(): List<Paciente>
    fun buscarPorDni(dni: String): Paciente?
    fun insertPaciente(paciente: Paciente)
    fun updatePaciente(paciente: Paciente)
    fun deletePaciente(paciente: Paciente)
}