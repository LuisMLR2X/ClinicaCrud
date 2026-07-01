package com.example.clinicacrud.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DoctorDao {
    @Query(value = "select * from Doctor")
    fun getDoctores(): List<DoctorEntity>

    @Query(value = "select * from Doctor where codigo = :codigo limit 1")
    fun buscarPorCodigo(codigo: String): DoctorEntity?

    @Insert
    fun insertDoctor(doctor: DoctorEntity)

    @Update
    fun updateDoctor(doctor: DoctorEntity)

    @Delete
    fun deleteDoctor(doctor: DoctorEntity)
}