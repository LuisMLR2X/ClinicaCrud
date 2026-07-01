package com.example.clinicacrud.presentation.cita

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.model.Paciente

private val ESTADOS = listOf("Pendiente", "Atendida", "Cancelada")

@Composable
fun CitaScreen(viewModel: CitaViewModel, doctores: List<Doctor>, pacientes: List<Paciente>, nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    var doctorCodigo by rememberSaveable { mutableStateOf(doctores.firstOrNull()?.codigo ?: "") }
    var pacienteId by rememberSaveable { mutableStateOf(pacientes.firstOrNull()?.id ?: 0L) }
    var fecha by rememberSaveable { mutableStateOf("") }
    var hora by rememberSaveable { mutableStateOf("") }
    var motivo by rememberSaveable { mutableStateOf("") }
    var diagnostico by rememberSaveable { mutableStateOf("") }
    var costo by rememberSaveable { mutableStateOf("") }
    var estado by rememberSaveable { mutableStateOf(ESTADOS[0]) }

    var idMod by rememberSaveable { mutableStateOf("") }
    var estadoMod by rememberSaveable { mutableStateOf(ESTADOS[0]) }
    var diagnosticoMod by rememberSaveable { mutableStateOf("") }
    var costoMod by rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            TopBar("Citas Médicas", nav, isDarkTheme, onToggleTheme)

            LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    SectionCard("Buscar por paciente (ID)", Icons.Filled.Search, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(viewModel.filtroPaciente, { viewModel.filtroPaciente = it }, Modifier.fillMaxWidth(), label = { Text("ID Paciente") })
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Registrar", Icons.Filled.Add, MaterialTheme.colorScheme.secondary) {
                        Text("Doctor", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        doctores.forEach { d -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(doctorCodigo == d.codigo, { doctorCodigo = d.codigo }); Text("${d.codigo} - ${d.nombres} ${d.apellidos}", color = MaterialTheme.colorScheme.onSurface) } }
                        Spacer(Modifier.height(8.dp))
                        Text("Paciente", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        pacientes.forEach { p -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(pacienteId == p.id, { pacienteId = p.id }); Text("${p.dni} - ${p.nombres} ${p.apellidos}", color = MaterialTheme.colorScheme.onSurface) } }
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(fecha, { fecha = it }, Modifier.fillMaxWidth(), label = { Text("Fecha (dd/MM/aaaa)") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(hora, { hora = it }, Modifier.fillMaxWidth(), label = { Text("Hora (HH:mm)") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(motivo, { motivo = it }, Modifier.fillMaxWidth(), label = { Text("Motivo de consulta") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(diagnostico, { diagnostico = it }, Modifier.fillMaxWidth(), label = { Text("Diagnóstico") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(costo, { costo = it }, Modifier.fillMaxWidth(), label = { Text("Costo (S/)") })
                        Spacer(Modifier.height(12.dp))
                        Button({
                            viewModel.agregar(Cita(doctorCodigo = doctorCodigo, pacienteId = pacienteId, fecha = fecha.trim(), hora = hora.trim(), motivoConsulta = motivo.trim(), diagnostico = diagnostico.trim(), costo = costo.toDoubleOrNull() ?: 0.0, estado = estado))
                            fecha = ""; hora = ""; motivo = ""; diagnostico = ""; costo = ""
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) { Text("REGISTRAR") }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Modificar (estado/diagnóstico/costo)", Icons.Filled.Edit, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(idMod, { idMod = it }, Modifier.fillMaxWidth(), label = { Text("⚠ ID cita a modificar") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(diagnosticoMod, { diagnosticoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo diagnóstico") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(costoMod, { costoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo costo") })
                        Spacer(Modifier.height(8.dp))
                        Text("Estado", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        ESTADOS.forEach { s -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(estadoMod == s, { estadoMod = s }); Text(s, color = MaterialTheme.colorScheme.onSurface) } }
                        Spacer(Modifier.height(12.dp))
                        Button({
                            val original = viewModel.citas.find { it.id == (idMod.toLongOrNull() ?: -1) }
                            if (original != null) {
                                viewModel.modificar(original.id, original.copy(
                                    diagnostico = diagnosticoMod.trim().ifBlank { original.diagnostico },
                                    costo = costoMod.toDoubleOrNull() ?: original.costo,
                                    estado = estadoMod
                                ))
                            }
                            idMod = ""; diagnosticoMod = ""; costoMod = ""
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("MODIFICAR") }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Listado (${viewModel.citasFiltradas.size})", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                }

                items(viewModel.citasFiltradas) { c ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("ID ${c.id} · Dr(a). ${c.doctorCodigo} · Pac. ${c.pacienteId}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("${c.fecha} ${c.hora} · ${c.estado} · S/ ${c.costo}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Motivo: ${c.motivoConsulta} · Dx: ${c.diagnostico}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton({ viewModel.eliminar(c) }) { Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    OutlinedButton({ nav.popBackStack() }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("Ir a Menú") }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun TopBar(title: String, nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Row(Modifier.fillMaxWidth().padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton({ nav.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Volver", tint = MaterialTheme.colorScheme.onPrimary) }
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.weight(1f))
            IconButton(onToggleTheme) { Icon(if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode, "Cambiar tema", tint = MaterialTheme.colorScheme.onPrimary) }
        }
    }
}

@Composable
private fun SectionCard(title: String, icon: ImageVector, accent: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = accent)
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}