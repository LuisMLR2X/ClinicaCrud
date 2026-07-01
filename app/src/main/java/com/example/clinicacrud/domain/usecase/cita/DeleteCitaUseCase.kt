// domain/usecase/cita/DeleteCitaUseCase.kt
package com.example.clinicacrud.domain.usecase.cita
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.repository.CitaRepository
class DeleteCitaUseCase(private val repo: CitaRepository) {
    operator fun invoke(cita: Cita) = repo.deleteCita(cita)
}