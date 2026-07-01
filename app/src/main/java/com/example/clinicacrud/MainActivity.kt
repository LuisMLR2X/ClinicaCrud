package com.example.clinicacrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
            var isDarkTheme by remember { mutableStateOf(false) }
            ClinicaCrudTheme(darkTheme = isDarkTheme) {
                AppNavegacion(especialidadVM, doctorVM, pacienteVM, citaVM, isDarkTheme) { isDarkTheme = !isDarkTheme }
            }
        }
    }
}

@Composable
fun AppNavegacion(
    especialidadVM: EspecialidadViewModel,
    doctorVM: DoctorViewModel,
    pacienteVM: PacienteViewModel,
    citaVM: CitaViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "home") {
        composable("home") { HomeScreen(nav, isDarkTheme, onToggleTheme) }
        composable("especialidades") { EspecialidadScreen(especialidadVM, nav, isDarkTheme, onToggleTheme) }
        composable("doctores") { DoctorScreen(doctorVM, especialidadVM.especialidades, nav, isDarkTheme, onToggleTheme) }
        composable("pacientes") { PacienteScreen(pacienteVM, nav, isDarkTheme, onToggleTheme) }
        composable("citas") { CitaScreen(citaVM, doctorVM.doctores, pacienteVM.pacientes, nav, isDarkTheme, onToggleTheme) }
    }
}

@Composable
fun HomeScreen(nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Fondo sólido corregido
    ) {
        Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = onToggleTheme) {
                Icon(
                    if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                    contentDescription = "Cambiar tema",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Column(
            Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Filled.LocalHospital,
                null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Clínica",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground // Contraste automático
            )
            Text(
                "Panel principal",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Texto secundario corregido
            )
            Spacer(Modifier.height(28.dp))

            HomeMenuButton("Especialidades", Icons.Filled.MedicalServices) { nav.navigate("especialidades") }
            Spacer(Modifier.height(12.dp))
            HomeMenuButton("Doctores", Icons.Filled.People) { nav.navigate("doctores") }
            Spacer(Modifier.height(12.dp))
            HomeMenuButton("Pacientes", Icons.Filled.People) { nav.navigate("pacientes") }
            Spacer(Modifier.height(12.dp))
            HomeMenuButton("Citas Médicas", Icons.Filled.CalendarMonth) { nav.navigate("citas") }
        }
    }
}

@Composable
private fun HomeMenuButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.85f).height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(icon, null, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}