// presentation/especialidad/EspecialidadScreen.kt
package com.example.clinicacrud.presentation.especialidad

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

@Composable
fun EspecialidadScreen(viewModel: EspecialidadViewModel, nav: NavController) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var idMod by rememberSaveable { mutableStateOf("") }
    var nombreMod by rememberSaveable { mutableStateOf("") }
    var descripcionMod by rememberSaveable { mutableStateOf("") }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("ESPECIALIDADES", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            Text("Registrar", fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
            OutlinedTextField(nombre, { nombre = it }, Modifier.fillMaxWidth(), label = { Text("Nombre") })
            OutlinedTextField(descripcion, { descripcion = it }, Modifier.fillMaxWidth(), label = { Text("Descripción") })
            Button({ viewModel.agregar(nombre.trim(), descripcion.trim()); nombre = ""; descripcion = "" },
                Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))) { Text("REGISTRAR") }

            Spacer(Modifier.height(20.dp))
            Text("Modificar", fontWeight = FontWeight.SemiBold, color = Color(0xFFE65100))
            OutlinedTextField(idMod, { idMod = it }, Modifier.fillMaxWidth(),
                label = { Text("⚠ ID a modificar", color = Color(0xFFE65100)) })
            OutlinedTextField(nombreMod, { nombreMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo nombre") })
            OutlinedTextField(descripcionMod, { descripcionMod = it }, Modifier.fillMaxWidth(), label = { Text("Nueva descripción") })
            Button({
                viewModel.modificar(idMod.toLongOrNull() ?: 0, nombreMod.trim(), descripcionMod.trim())
                idMod = ""; nombreMod = ""; descripcionMod = ""
            }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFFE65100))) { Text("MODIFICAR") }

            Spacer(Modifier.height(20.dp))
            Text("Listar / Eliminar (${viewModel.especialidades.size})", fontWeight = FontWeight.SemiBold, color = Color(0xFFC62828))
        }

        items(viewModel.especialidades) { e ->
            Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("ID ${e.id} - ${e.nombre}", fontWeight = FontWeight.Bold)
                    Text(e.descripcion)
                }
                Button({ viewModel.eliminar(e) }, colors = ButtonDefaults.buttonColors(Color(0xFFC62828))) { Text("Eliminar") }
            }
            HorizontalDivider()
        }

        item {
            Spacer(Modifier.height(16.dp))
            Button({ nav.popBackStack() }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color.Gray)) { Text("Ir a Menu") }
        }
    }
}