package com.example.clinicacrud.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CitaDao {
    @Query(value = "select * from Cita")
    fun getCitas(): List<CitaEntity>

    @Insert
    fun insertCita(cita: CitaEntity)

    @Update
    fun updateCita(cita: CitaEntity)

    @Delete
    fun deleteCita(cita: CitaEntity)
}