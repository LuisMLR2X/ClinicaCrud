// presentation/paciente/PacienteScreen.kt
package com.example.clinicacrud.presentation.paciente

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
import com.example.clinicacrud.domain.model.Paciente

@Composable
fun PacienteScreen(viewModel: PacienteViewModel, nav: NavController) {
    var dni by rememberSaveable { mutableStateOf("") }
    var nombres by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var idMod by rememberSaveable { mutableStateOf("") }
    var dniMod by rememberSaveable { mutableStateOf("") }
    var nombresMod by rememberSaveable { mutableStateOf("") }
    var apellidosMod by rememberSaveable { mutableStateOf("") }
    var telefonoMod by rememberSaveable { mutableStateOf("") }
    var dniBusqueda by rememberSaveable { mutableStateOf("") }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("PACIENTES", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Buscar", fontWeight = FontWeight.SemiBold, color = Color(0xFF01579B))
            OutlinedTextField(dniBusqueda, { dniBusqueda = it }, Modifier.fillMaxWidth(), label = { Text("DNI") })
            Button({ viewModel.buscar(dniBusqueda.trim()) }, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF01579B))) { Text("BUSCAR") }
            viewModel.busqueda?.let {
                Text("${it.dni} | ${it.nombres} ${it.apellidos} | Tel: ${it.telefono}")
                Button({ viewModel.limpiarBusqueda(); dniBusqueda = "" }, Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("CERRAR") }
            }

            Spacer(Modifier.height(20.dp))
            Text("Registrar", fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
            OutlinedTextField(dni, { dni = it }, Modifier.fillMaxWidth(), label = { Text("DNI") })
            OutlinedTextField(nombres, { nombres = it }, Modifier.fillMaxWidth(), label = { Text("Nombres") })
            OutlinedTextField(apellidos, { apellidos = it }, Modifier.fillMaxWidth(), label = { Text("Apellidos") })
            OutlinedTextField(telefono, { telefono = it }, Modifier.fillMaxWidth(), label = { Text("Teléfono") })
            Button({
                viewModel.agregar(Paciente(dni = dni.trim(), nombres = nombres.trim(), apellidos = apellidos.trim(), telefono = telefono.trim()))
                dni = ""; nombres = ""; apellidos = ""; telefono = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))) { Text("REGISTRAR") }

            Spacer(Modifier.height(20.dp))
            Text("Modificar", fontWeight = FontWeight.SemiBold, color = Color(0xFFE65100))
            OutlinedTextField(idMod, { idMod = it }, Modifier.fillMaxWidth(),
                label = { Text("⚠ ID a modificar", color = Color(0xFFE65100)) })
            OutlinedTextField(dniMod, { dniMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo DNI") })
            OutlinedTextField(nombresMod, { nombresMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos nombres") })
            OutlinedTextField(apellidosMod, { apellidosMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos apellidos") })
            OutlinedTextField(telefonoMod, { telefonoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo teléfono") })
            Button({
                viewModel.modificar(idMod.toLongOrNull() ?: 0, Paciente(dni = dniMod.trim(), nombres = nombresMod.trim(), apellidos = apellidosMod.trim(), telefono = telefonoMod.trim()))
                idMod = ""; dniMod = ""; nombresMod = ""; apellidosMod = ""; telefonoMod = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFE65100))) { Text("MODIFICAR") }

            Spacer(Modifier.height(20.dp))
            Text("Listar / Eliminar (${viewModel.pacientes.size})", fontWeight = FontWeight.SemiBold, color = Color(0xFFC62828))
        }

        items(viewModel.pacientes) { p ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("${p.dni} - ${p.nombres} ${p.apellidos}", fontWeight = FontWeight.Bold)
                    Text("Tel: ${p.telefono}")
                }
                Button({ viewModel.eliminar(p) }, colors = ButtonDefaults.buttonColors(Color(0xFFC62828))) { Text("Eliminar") }
            }
            HorizontalDivider()
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button({ nav.popBackStack() }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("Ir a Menu") }
        }
    }
}