package com.example.clinicacrud.domain.usecase.paciente
import com.example.clinicacrud.domain.repository.PacienteRepository
class GetPacientesUseCase(private val repo: PacienteRepository) {
    operator fun invoke() = repo.getPacientes()
}