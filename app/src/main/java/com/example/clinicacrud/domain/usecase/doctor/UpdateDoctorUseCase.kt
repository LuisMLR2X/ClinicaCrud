package com.example.clinicacrud.domain.usecase.doctor
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.repository.DoctorRepository
class UpdateDoctorUseCase(private val repo: DoctorRepository) {
    operator fun invoke(doctor: Doctor) = repo.updateDoctor(doctor)
}