package com.example.clinicacrud.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EspecialidadEntity::class, DoctorEntity::class, PacienteEntity::class, CitaEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun especialidadDao(): EspecialidadDao
    abstract fun doctorDao(): DoctorDao
    abstract fun pacienteDao(): PacienteDao
    abstract fun citaDao(): CitaDao
}