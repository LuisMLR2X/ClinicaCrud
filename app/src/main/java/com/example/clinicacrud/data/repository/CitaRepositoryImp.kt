package com.example.clinicacrud.data.repository

import com.example.clinicacrud.data.local.CitaDao
import com.example.clinicacrud.data.local.CitaEntity
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.repository.CitaRepository

class CitaRepositoryImp(private val dao: CitaDao) : CitaRepository {
    override fun getCitas(): List<Cita> =
        dao.getCitas().map { Cita(it.id, it.doctorCodigo, it.pacienteId, it.fecha, it.hora, it.motivoConsulta, it.diagnostico, it.costo, it.estado) }

    override fun insertCita(cita: Cita) {
        dao.insertCita(CitaEntity(doctorCodigo = cita.doctorCodigo, pacienteId = cita.pacienteId, fecha = cita.fecha,
            hora = cita.hora, motivoConsulta = cita.motivoConsulta, diagnostico = cita.diagnostico, costo = cita.costo, estado = cita.estado))
    }

    override fun updateCita(cita: Cita) {
        dao.updateCita(CitaEntity(cita.id, cita.doctorCodigo, cita.pacienteId, cita.fecha, cita.hora, cita.motivoConsulta, cita.diagnostico, cita.costo, cita.estado))
    }

    override fun deleteCita(cita: Cita) {
        dao.deleteCita(CitaEntity(cita.id, cita.doctorCodigo, cita.pacienteId, cita.fecha, cita.hora, cita.motivoConsulta, cita.diagnostico, cita.costo, cita.estado))
    }
}