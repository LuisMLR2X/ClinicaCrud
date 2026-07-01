package com.example.clinicacrud.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EspecialidadDao {
    @Query(value = "select * from Especialidad")
    fun getEspecialidades(): List<EspecialidadEntity>

    @Insert
    fun insertEspecialidad(especialidad: EspecialidadEntity)

    @Update
    fun updateEspecialidad(especialidad: EspecialidadEntity)

    @Delete
    fun deleteEspecialidad(especialidad: EspecialidadEntity)
}