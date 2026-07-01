package com.example.clinicacrud.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PacienteDao {
    @Query(value = "select * from Paciente")
    fun getPacientes(): List<PacienteEntity>

    @Query(value = "select * from Paciente where dni = :dni limit 1")
    fun buscarPorDni(dni: String): PacienteEntity?

    @Insert
    fun insertPaciente(paciente: PacienteEntity)

    @Update
    fun updatePaciente(paciente: PacienteEntity)

    @Delete
    fun deletePaciente(paciente: PacienteEntity)
}