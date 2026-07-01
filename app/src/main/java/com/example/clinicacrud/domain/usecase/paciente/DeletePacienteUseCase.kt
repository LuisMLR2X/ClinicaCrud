package com.example.clinicacrud.domain.usecase.paciente
import com.example.clinicacrud.domain.model.Paciente
import com.example.clinicacrud.domain.repository.PacienteRepository
class DeletePacienteUseCase(private val repo: PacienteRepository) {
    operator fun invoke(paciente: Paciente) = repo.deletePaciente(paciente)
}