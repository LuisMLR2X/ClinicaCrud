package com.example.clinicacrud.domain.usecase.cita
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.repository.CitaRepository
class AddCitaUseCase(private val repo: CitaRepository) {
    operator fun invoke(cita: Cita) = repo.insertCita(cita)
}