package com.example.clinicacrud.presentation.paciente

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinicacrud.domain.model.Paciente
import com.example.clinicacrud.domain.usecase.paciente.*

class PacienteViewModel(
    private val getPacientesUseCase: GetPacientesUseCase,
    private val addPacienteUseCase: AddPacienteUseCase,
    private val updatePacienteUseCase: UpdatePacienteUseCase,
    private val deletePacienteUseCase: DeletePacienteUseCase,
    private val searchPacienteUseCase: SearchPacienteUseCase
) {
    var pacientes by mutableStateOf(getPacientesUseCase())
        private set
    var busqueda by mutableStateOf<Paciente?>(null)
        private set

    private fun actualizar() { pacientes = getPacientesUseCase() }

    fun agregar(p: Paciente) {
        if (p.dni.isBlank() || p.nombres.isBlank()) return
        addPacienteUseCase(p)
        actualizar()
    }

    fun modificar(id: Long, p: Paciente) {
        updatePacienteUseCase(p.copy(id = id))
        actualizar()
    }

    fun eliminar(p: Paciente) {
        deletePacienteUseCase(p)
        actualizar()
    }

    fun buscar(dni: String) { busqueda = searchPacienteUseCase(dni) }
    fun limpiarBusqueda() { busqueda = null }
}