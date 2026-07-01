// domain/usecase/cita/UpdateCitaUseCase.kt
package com.example.clinicacrud.domain.usecase.cita
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.repository.CitaRepository
class UpdateCitaUseCase(private val repo: CitaRepository) {
    operator fun invoke(cita: Cita) = repo.updateCita(cita)
}