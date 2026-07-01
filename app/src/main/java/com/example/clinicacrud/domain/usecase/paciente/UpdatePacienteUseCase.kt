// domain/usecase/paciente/UpdatePacienteUseCase.kt
package com.example.clinicacrud.domain.usecase.paciente
import com.example.clinicacrud.domain.model.Paciente
import com.example.clinicacrud.domain.repository.PacienteRepository
class UpdatePacienteUseCase(private val repo: PacienteRepository) {
    operator fun invoke(paciente: Paciente) = repo.updatePaciente(paciente)
}