package com.example.clinicacrud.data.repository

import com.example.clinicacrud.data.local.DoctorDao
import com.example.clinicacrud.data.local.DoctorEntity
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.repository.DoctorRepository

class DoctorRepositoryImp(private val dao: DoctorDao) : DoctorRepository {
    override fun getDoctores(): List<Doctor> =
        dao.getDoctores().map { Doctor(it.codigo, it.nombres, it.apellidos, it.sexo, it.fechaNacimiento, it.especialidadId) }

    override fun buscarPorCodigo(codigo: String): Doctor? =
        dao.buscarPorCodigo(codigo)?.let { Doctor(it.codigo, it.nombres, it.apellidos, it.sexo, it.fechaNacimiento, it.especialidadId) }

    override fun insertDoctor(doctor: Doctor) {
        dao.insertDoctor(DoctorEntity(doctor.codigo, doctor.nombres, doctor.apellidos, doctor.sexo, doctor.fechaNacimiento, doctor.especialidadId))
    }

    override fun updateDoctor(doctor: Doctor) {
        dao.updateDoctor(DoctorEntity(doctor.codigo, doctor.nombres, doctor.apellidos, doctor.sexo, doctor.fechaNacimiento, doctor.especialidadId))
    }

    override fun deleteDoctor(doctor: Doctor) {
        dao.deleteDoctor(DoctorEntity(doctor.codigo, doctor.nombres, doctor.apellidos, doctor.sexo, doctor.fechaNacimiento, doctor.especialidadId))
    }
}