package com.example.clinicacrud.domain.repository

import com.example.clinicacrud.domain.model.Cita

interface CitaRepository {
    fun getCitas(): List<Cita>
    fun insertCita(cita: Cita)
    fun updateCita(cita: Cita)
    fun deleteCita(cita: Cita)
}