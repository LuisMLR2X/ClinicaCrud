package com.example.clinicacrud.presentation.doctor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.usecase.doctor.*

class DoctorViewModel(
    private val getDoctoresUseCase: GetDoctoresUseCase,
    private val addDoctorUseCase: AddDoctorUseCase,
    private val updateDoctorUseCase: UpdateDoctorUseCase,
    private val deleteDoctorUseCase: DeleteDoctorUseCase,
    private val searchDoctorUseCase: SearchDoctorUseCase
) {
    var doctores by mutableStateOf(getDoctoresUseCase())
        private set
    var busqueda by mutableStateOf<Doctor?>(null)
        private set

    private fun actualizar() { doctores = getDoctoresUseCase() }

    fun agregar(d: Doctor) {
        if (d.codigo.isBlank() || d.nombres.isBlank()) return
        addDoctorUseCase(d)
        actualizar()
    }

    fun modificar(d: Doctor) {
        if (d.codigo.isBlank()) return
        updateDoctorUseCase(d)
        actualizar()
    }

    fun eliminar(d: Doctor) {
        deleteDoctorUseCase(d)
        actualizar()
    }

    fun buscar(codigo: String) { busqueda = searchDoctorUseCase(codigo) }
    fun limpiarBusqueda() { busqueda = null }
}