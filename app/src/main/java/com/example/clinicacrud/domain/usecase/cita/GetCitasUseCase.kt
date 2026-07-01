package com.example.clinicacrud.domain.usecase.cita
import com.example.clinicacrud.domain.repository.CitaRepository
class GetCitasUseCase(private val repo: CitaRepository) {
    operator fun invoke() = repo.getCitas()
}