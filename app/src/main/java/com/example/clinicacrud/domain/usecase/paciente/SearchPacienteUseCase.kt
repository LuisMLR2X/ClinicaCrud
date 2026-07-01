package com.example.clinicacrud.domain.usecase.paciente
import com.example.clinicacrud.domain.repository.PacienteRepository
class SearchPacienteUseCase(private val repo: PacienteRepository) {
    operator fun invoke(dni: String) = repo.buscarPorDni(dni)
}