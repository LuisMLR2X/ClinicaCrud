// domain/usecase/doctor/GetDoctoresUseCase.kt
package com.example.clinicacrud.domain.usecase.doctor
import com.example.clinicacrud.domain.repository.DoctorRepository
class GetDoctoresUseCase(private val repo: DoctorRepository) {
    operator fun invoke() = repo.getDoctores()
}