// domain/usecase/doctor/AddDoctorUseCase.kt
package com.example.clinicacrud.domain.usecase.doctor
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.repository.DoctorRepository
class AddDoctorUseCase(private val repo: DoctorRepository) {
    operator fun invoke(doctor: Doctor) = repo.insertDoctor(doctor)
}