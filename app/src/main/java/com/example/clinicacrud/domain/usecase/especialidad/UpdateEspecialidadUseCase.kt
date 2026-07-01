package com.example.clinicacrud.domain.usecase.especialidad
import com.example.clinicacrud.domain.model.Especialidad
import com.example.clinicacrud.domain.repository.EspecialidadRepository
class UpdateEspecialidadUseCase(private val repo: EspecialidadRepository) {
    operator fun invoke(especialidad: Especialidad) = repo.updateEspecialidad(especialidad)
}