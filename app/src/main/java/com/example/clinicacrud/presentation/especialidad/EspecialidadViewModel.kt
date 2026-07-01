// presentation/especialidad/EspecialidadViewModel.kt
package com.example.clinicacrud.presentation.especialidad

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinicacrud.domain.model.Especialidad
import com.example.clinicacrud.domain.usecase.especialidad.*

class EspecialidadViewModel(
    private val getEspecialidadesUseCase: GetEspecialidadesUseCase,
    private val addEspecialidadUseCase: AddEspecialidadUseCase,
    private val updateEspecialidadUseCase: UpdateEspecialidadUseCase,
    private val deleteEspecialidadUseCase: DeleteEspecialidadUseCase
) {
    var especialidades by mutableStateOf(getEspecialidadesUseCase())
        private set

    private fun actualizar() { especialidades = getEspecialidadesUseCase() }

    fun agregar(nombre: String, descripcion: String) {
        if (nombre.isBlank()) return
        addEspecialidadUseCase(Especialidad(nombre = nombre, descripcion = descripcion))
        actualizar()
    }

    fun modificar(id: Long, nombre: String, descripcion: String) {
        if (nombre.isBlank()) return
        updateEspecialidadUseCase(Especialidad(id, nombre, descripcion))
        actualizar()
    }

    fun eliminar(especialidad: Especialidad) {
        deleteEspecialidadUseCase(especialidad)
        actualizar()
    }
}