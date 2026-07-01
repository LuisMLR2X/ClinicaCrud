// presentation/cita/CitaScreen.kt
package com.example.clinicacrud.presentation.cita

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.clinicacrud.domain.model.Cita
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.model.Paciente

private val ESTADOS = listOf("Pendiente", "Atendida", "Cancelada")

@Composable
fun CitaScreen(viewModel: CitaViewModel, doctores: List<Doctor>, pacientes: List<Paciente>, nav: NavController) {
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

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("CITAS MÉDICAS", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Buscar por paciente (ID)", fontWeight = FontWeight.SemiBold, color = Color(0xFF01579B))
            OutlinedTextField(viewModel.filtroPaciente, { viewModel.filtroPaciente = it }, Modifier.fillMaxWidth(), label = { Text("ID Paciente") })

            Spacer(Modifier.height(20.dp))
            Text("Registrar", fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
            Text("Doctor:")
            doctores.forEach { d ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(doctorCodigo == d.codigo, { doctorCodigo = d.codigo })
                    Text("${d.codigo} - ${d.nombres} ${d.apellidos}")
                }
            }
            Text("Paciente:")
            pacientes.forEach { p ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(pacienteId == p.id, { pacienteId = p.id })
                    Text("${p.dni} - ${p.nombres} ${p.apellidos}")
                }
            }
            OutlinedTextField(fecha, { fecha = it }, Modifier.fillMaxWidth(), label = { Text("Fecha (dd/MM/aaaa)") })
            OutlinedTextField(hora, { hora = it }, Modifier.fillMaxWidth(), label = { Text("Hora (HH:mm)") })
            OutlinedTextField(motivo, { motivo = it }, Modifier.fillMaxWidth(), label = { Text("Motivo de consulta") })
            OutlinedTextField(diagnostico, { diagnostico = it }, Modifier.fillMaxWidth(), label = { Text("Diagnóstico") })
            OutlinedTextField(costo, { costo = it }, Modifier.fillMaxWidth(), label = { Text("Costo (S/)") })
            Button({
                viewModel.agregar(Cita(doctorCodigo = doctorCodigo, pacienteId = pacienteId, fecha = fecha.trim(), hora = hora.trim(),
                    motivoConsulta = motivo.trim(), diagnostico = diagnostico.trim(), costo = costo.toDoubleOrNull() ?: 0.0, estado = estado))
                fecha = ""; hora = ""; motivo = ""; diagnostico = ""; costo = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))) { Text("REGISTRAR") }

            Spacer(Modifier.height(20.dp))
            Text("Modificar (estado/diagnóstico/costo)", fontWeight = FontWeight.SemiBold, color = Color(0xFFE65100))
            OutlinedTextField(idMod, { idMod = it }, Modifier.fillMaxWidth(),
                label = { Text("⚠ ID cita a modificar", color = Color(0xFFE65100)) })
            OutlinedTextField(diagnosticoMod, { diagnosticoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo diagnóstico") })
            OutlinedTextField(costoMod, { costoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo costo") })
            Text("Estado:")
            ESTADOS.forEach { s ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(estadoMod == s, { estadoMod = s }); Text(s)
                }
            }
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
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFE65100))) { Text("MODIFICAR") }

            Spacer(Modifier.height(20.dp))
            Text("Listar / Eliminar (${viewModel.citasFiltradas.size})", fontWeight = FontWeight.SemiBold, color = Color(0xFFC62828))
        }

        items(viewModel.citasFiltradas) { c ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("ID ${c.id} | Dr(a). ${c.doctorCodigo} | Pac. ${c.pacienteId}", fontWeight = FontWeight.Bold)
                    Text("${c.fecha} ${c.hora} | ${c.estado} | S/ ${c.costo}")
                    Text("Motivo: ${c.motivoConsulta} | Dx: ${c.diagnostico}")
                }
                Button({ viewModel.eliminar(c) }, colors = ButtonDefaults.buttonColors(Color(0xFFC62828))) { Text("Eliminar") }
            }
            HorizontalDivider()
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button({ nav.popBackStack() }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("Ir a Menu") }
        }
    }
}