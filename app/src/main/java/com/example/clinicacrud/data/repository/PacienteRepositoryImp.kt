package com.example.clinicacrud.data.repository

import com.example.clinicacrud.data.local.PacienteDao
import com.example.clinicacrud.data.local.PacienteEntity
import com.example.clinicacrud.domain.model.Paciente
import com.example.clinicacrud.domain.repository.PacienteRepository

class PacienteRepositoryImp(private val dao: PacienteDao) : PacienteRepository {
    override fun getPacientes(): List<Paciente> =
        dao.getPacientes().map { Paciente(it.id, it.dni, it.nombres, it.apellidos, it.telefono) }

    override fun buscarPorDni(dni: String): Paciente? =
        dao.buscarPorDni(dni)?.let { Paciente(it.id, it.dni, it.nombres, it.apellidos, it.telefono) }

    override fun insertPaciente(paciente: Paciente) {
        dao.insertPaciente(PacienteEntity(dni = paciente.dni, nombres = paciente.nombres, apellidos = paciente.apellidos, telefono = paciente.telefono))
    }

    override fun updatePaciente(paciente: Paciente) {
        dao.updatePaciente(PacienteEntity(paciente.id, paciente.dni, paciente.nombres, paciente.apellidos, paciente.telefono))
    }

    override fun deletePaciente(paciente: Paciente) {
        dao.deletePaciente(PacienteEntity(paciente.id, paciente.dni, paciente.nombres, paciente.apellidos, paciente.telefono))
    }
}