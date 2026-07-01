package com.example.clinicacrud.domain.usecase.paciente
import com.example.clinicacrud.domain.model.Paciente
import com.example.clinicacrud.domain.repository.PacienteRepository
class AddPacienteUseCase(private val repo: PacienteRepository) {
    operator fun invoke(paciente: Paciente) = repo.insertPaciente(paciente)
}