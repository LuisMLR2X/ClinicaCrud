// presentation/doctor/DoctorScreen.kt
package com.example.clinicacrud.presentation.doctor

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
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.model.Especialidad

@Composable
fun DoctorScreen(viewModel: DoctorViewModel, especialidades: List<Especialidad>, nav: NavController) {
    var codigo by rememberSaveable { mutableStateOf("") }
    var nombres by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var sexo by rememberSaveable { mutableStateOf("M") }
    var fechaNac by rememberSaveable { mutableStateOf("") }
    var especialidad by rememberSaveable { mutableStateOf(especialidades.firstOrNull()?.id ?: 0L) }

    var codigoMod by rememberSaveable { mutableStateOf("") }
    var nombresMod by rememberSaveable { mutableStateOf("") }
    var apellidosMod by rememberSaveable { mutableStateOf("") }
    var sexoMod by rememberSaveable { mutableStateOf("M") }
    var fechaNacMod by rememberSaveable { mutableStateOf("") }
    var especialidadMod by rememberSaveable { mutableStateOf(especialidades.firstOrNull()?.id ?: 0L) }

    var codigoBusqueda by rememberSaveable { mutableStateOf("") }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("DOCTORES", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Buscar", fontWeight = FontWeight.SemiBold, color = Color(0xFF01579B))
            OutlinedTextField(codigoBusqueda, { codigoBusqueda = it }, Modifier.fillMaxWidth(), label = { Text("Código") })
            Button({ viewModel.buscar(codigoBusqueda.trim()) }, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF01579B))) { Text("BUSCAR") }
            viewModel.busqueda?.let {
                Text("${it.codigo} | ${it.nombres} ${it.apellidos} | ${it.sexo} | Especialidad ID: ${it.especialidadId}")
                Button({ viewModel.limpiarBusqueda(); codigoBusqueda = "" }, Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("CERRAR") }
            }

            Spacer(Modifier.height(20.dp))
            Text("Registrar", fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
            OutlinedTextField(codigo, { codigo = it }, Modifier.fillMaxWidth(), label = { Text("Código único") })
            OutlinedTextField(nombres, { nombres = it }, Modifier.fillMaxWidth(), label = { Text("Nombres") })
            OutlinedTextField(apellidos, { apellidos = it }, Modifier.fillMaxWidth(), label = { Text("Apellidos") })
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(sexo == "M", { sexo = "M" }); Text("M")
                Spacer(Modifier.width(16.dp))
                RadioButton(sexo == "F", { sexo = "F" }); Text("F")
            }
            OutlinedTextField(fechaNac, { fechaNac = it }, Modifier.fillMaxWidth(), label = { Text("Fecha nacimiento (dd/MM/aaaa)") })
            Text("Especialidad:")
            especialidades.forEach { e ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(especialidad == e.id, { especialidad = e.id })
                    Text(e.nombre)
                }
            }
            Button({
                viewModel.agregar(Doctor(codigo.trim(), nombres.trim(), apellidos.trim(), sexo, fechaNac.trim(), especialidad))
                codigo = ""; nombres = ""; apellidos = ""; fechaNac = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))) { Text("REGISTRAR") }

            Spacer(Modifier.height(20.dp))
            Text("Modificar", fontWeight = FontWeight.SemiBold, color = Color(0xFFE65100))
            OutlinedTextField(codigoMod, { codigoMod = it }, Modifier.fillMaxWidth(),
                label = { Text("⚠ Código a modificar", color = Color(0xFFE65100)) })
            OutlinedTextField(nombresMod, { nombresMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos nombres") })
            OutlinedTextField(apellidosMod, { apellidosMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos apellidos") })
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(sexoMod == "M", { sexoMod = "M" }); Text("M")
                Spacer(Modifier.width(16.dp))
                RadioButton(sexoMod == "F", { sexoMod = "F" }); Text("F")
            }
            OutlinedTextField(fechaNacMod, { fechaNacMod = it }, Modifier.fillMaxWidth(), label = { Text("Nueva fecha nacimiento") })
            Text("Especialidad:")
            especialidades.forEach { e ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(especialidadMod == e.id, { especialidadMod = e.id })
                    Text(e.nombre)
                }
            }
            Button({
                viewModel.modificar(Doctor(codigoMod.trim(), nombresMod.trim(), apellidosMod.trim(), sexoMod, fechaNacMod.trim(), especialidadMod))
                codigoMod = ""; nombresMod = ""; apellidosMod = ""; fechaNacMod = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFE65100))) { Text("MODIFICAR") }

            Spacer(Modifier.height(20.dp))
            Text("Listar / Eliminar (${viewModel.doctores.size})", fontWeight = FontWeight.SemiBold, color = Color(0xFFC62828))
        }

        items(viewModel.doctores) { d ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("${d.codigo} - ${d.nombres} ${d.apellidos}", fontWeight = FontWeight.Bold)
                    Text("${d.sexo} | Especialidad ID: ${d.especialidadId}")
                }
                Button({ viewModel.eliminar(d) }, colors = ButtonDefaults.buttonColors(Color(0xFFC62828))) { Text("Eliminar") }
            }
            HorizontalDivider()
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button({ nav.navigate("especialidades") }, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF1565C0))) { Text("Ir a Especialidades") }
            Spacer(Modifier.height(6.dp))
            Button({ nav.popBackStack() }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("Ir a Menu") }
        }
    }
}