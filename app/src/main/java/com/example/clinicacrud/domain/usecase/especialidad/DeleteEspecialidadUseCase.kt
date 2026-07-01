package com.example.clinicacrud.domain.usecase.especialidad
import com.example.clinicacrud.domain.model.Especialidad
import com.example.clinicacrud.domain.repository.EspecialidadRepository
class DeleteEspecialidadUseCase(private val repo: EspecialidadRepository) {
    operator fun invoke(especialidad: Especialidad) = repo.deleteEspecialidad(especialidad)
}