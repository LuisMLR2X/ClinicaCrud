package com.example.clinicacrud.domain.usecase.especialidad
import com.example.clinicacrud.domain.repository.EspecialidadRepository
class GetEspecialidadesUseCase(private val repo: EspecialidadRepository) {
    operator fun invoke() = repo.getEspecialidades()
}