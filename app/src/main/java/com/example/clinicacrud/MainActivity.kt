// MainActivity.kt
package com.example.clinicacrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.clinicacrud.data.local.AppDatabase
import com.example.clinicacrud.data.repository.*
import com.example.clinicacrud.domain.usecase.cita.*
import com.example.clinicacrud.domain.usecase.doctor.*
import com.example.clinicacrud.domain.usecase.especialidad.*
import com.example.clinicacrud.domain.usecase.paciente.*
import com.example.clinicacrud.presentation.cita.CitaScreen
import com.example.clinicacrud.presentation.cita.CitaViewModel
import com.example.clinicacrud.presentation.doctor.DoctorScreen
import com.example.clinicacrud.presentation.doctor.DoctorViewModel
import com.example.clinicacrud.presentation.especialidad.EspecialidadScreen
import com.example.clinicacrud.presentation.especialidad.EspecialidadViewModel
import com.example.clinicacrud.presentation.paciente.PacienteScreen
import com.example.clinicacrud.presentation.paciente.PacienteViewModel
import com.example.clinicacrud.ui.theme.ClinicaCrudTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "clinica_db")
            .allowMainThreadQueries().build()

        val especialidadRepo = EspecialidadRepositoryImp(db.especialidadDao())
        val doctorRepo = DoctorRepositoryImp(db.doctorDao())
        val pacienteRepo = PacienteRepositoryImp(db.pacienteDao())
        val citaRepo = CitaRepositoryImp(db.citaDao())

        val especialidadVM = EspecialidadViewModel(
            GetEspecialidadesUseCase(especialidadRepo), AddEspecialidadUseCase(especialidadRepo),
            UpdateEspecialidadUseCase(especialidadRepo), DeleteEspecialidadUseCase(especialidadRepo)
        )
        val doctorVM = DoctorViewModel(
            GetDoctoresUseCase(doctorRepo), AddDoctorUseCase(doctorRepo),
            UpdateDoctorUseCase(doctorRepo), DeleteDoctorUseCase(doctorRepo), SearchDoctorUseCase(doctorRepo)
        )
        val pacienteVM = PacienteViewModel(
            GetPacientesUseCase(pacienteRepo), AddPacienteUseCase(pacienteRepo),
            UpdatePacienteUseCase(pacienteRepo), DeletePacienteUseCase(pacienteRepo), SearchPacienteUseCase(pacienteRepo)
        )
        val citaVM = CitaViewModel(
            GetCitasUseCase(citaRepo), AddCitaUseCase(citaRepo), UpdateCitaUseCase(citaRepo), DeleteCitaUseCase(citaRepo)
        )

        setContent {
            ClinicaCrudTheme { AppNavegacion(especialidadVM, doctorVM, pacienteVM, citaVM) }
        }
    }
}

@Composable
fun AppNavegacion(
    especialidadVM: EspecialidadViewModel,
    doctorVM: DoctorViewModel,
    pacienteVM: PacienteViewModel,
    citaVM: CitaViewModel
) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "home") {
        composable("home") { HomeScreen(nav) }
        composable("especialidades") { EspecialidadScreen(especialidadVM, nav) }

        composable("doctores") { DoctorScreen(doctorVM, especialidadVM.especialidades, nav) }
        composable("pacientes") { PacienteScreen(pacienteVM, nav) }
        composable("citas") { CitaScreen(citaVM, doctorVM.doctores, pacienteVM.pacientes, nav) }
    }
}

@Composable
fun HomeScreen(nav: NavController) {
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("CLÍNICA - MENU PRINCIPAL", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { nav.navigate("especialidades") }) { Text("Especialidades") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate("doctores") }) { Text("Doctores") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate("pacientes") }) { Text("Pacientes") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate("citas") }) { Text("Citas Médicas") }
    }
}