// presentation/cita/CitaViewModel.kt
package com.example.clinicacrud.presentation.cita

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.usecase.cita.*

class CitaViewModel(
    private val getCitasUseCase: GetCitasUseCase,
    private val addCitaUseCase: AddCitaUseCase,
    private val updateCitaUseCase: UpdateCitaUseCase,
    private val deleteCitaUseCase: DeleteCitaUseCase
) {
    var citas by mutableStateOf(getCitasUseCase())
        private set
    var filtroPaciente by mutableStateOf("")

    val citasFiltradas get() = if (filtroPaciente.isBlank()) citas
    else citas.filter { it.pacienteId.toString() == filtroPaciente.trim() }

    private fun actualizar() { citas = getCitasUseCase() }

    fun agregar(c: Cita) {
        if (c.doctorCodigo.isBlank() || c.fecha.isBlank()) return
        addCitaUseCase(c)
        actualizar()
    }

    fun modificar(id: Long, c: Cita) {
        updateCitaUseCase(c.copy(id = id))
        actualizar()
    }

    fun eliminar(c: Cita) {
        deleteCitaUseCase(c)
        actualizar()
    }
}