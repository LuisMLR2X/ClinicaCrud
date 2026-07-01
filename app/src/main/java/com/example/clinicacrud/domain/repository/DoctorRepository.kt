package com.example.clinicacrud.domain.repository

import com.example.clinicacrud.domain.model.Doctor

interface DoctorRepository {
    fun getDoctores(): List<Doctor>
    fun buscarPorCodigo(codigo: String): Doctor?
    fun insertDoctor(doctor: Doctor)
    fun updateDoctor(doctor: Doctor)
    fun deleteDoctor(doctor: Doctor)
}