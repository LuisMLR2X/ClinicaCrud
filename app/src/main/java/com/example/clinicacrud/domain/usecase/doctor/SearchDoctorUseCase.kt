package com.example.clinicacrud.domain.usecase.doctor
import com.example.clinicacrud.domain.repository.DoctorRepository
class SearchDoctorUseCase(private val repo: DoctorRepository) {
    operator fun invoke(codigo: String) = repo.buscarPorCodigo(codigo)
}