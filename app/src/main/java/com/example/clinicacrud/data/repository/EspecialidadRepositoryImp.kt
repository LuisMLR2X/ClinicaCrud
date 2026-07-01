// data/repository/EspecialidadRepositoryImp.kt
package com.example.clinicacrud.data.repository

import com.example.clinicacrud.data.local.EspecialidadDao
import com.example.clinicacrud.data.local.EspecialidadEntity
import com.example.clinicacrud.domain.model.Especialidad
import com.example.clinicacrud.domain.repository.EspecialidadRepository

class EspecialidadRepositoryImp(private val dao: EspecialidadDao) : EspecialidadRepository {
    override fun getEspecialidades(): List<Especialidad> =
        dao.getEspecialidades().map { Especialidad(it.id, it.nombre, it.descripcion) }

    override fun insertEspecialidad(especialidad: Especialidad) {
        dao.insertEspecialidad(EspecialidadEntity(nombre = especialidad.nombre, descripcion = especialidad.descripcion))
    }

    override fun updateEspecialidad(especialidad: Especialidad) {
        dao.updateEspecialidad(EspecialidadEntity(especialidad.id, especialidad.nombre, especialidad.descripcion))
    }

    override fun deleteEspecialidad(especialidad: Especialidad) {
        dao.deleteEspecialidad(EspecialidadEntity(especialidad.id, especialidad.nombre, especialidad.descripcion))
    }
}