package com.example.clinicacrud.presentation.paciente

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
import com.example.clinicacrud.domain.model.Paciente

@Composable
fun PacienteScreen(viewModel: PacienteViewModel, nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    var dni by rememberSaveable { mutableStateOf("") }
    var nombres by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var dniBusquedaMod by rememberSaveable { mutableStateOf("") }
    var dniMod by rememberSaveable { mutableStateOf("") }
    var nombresMod by rememberSaveable { mutableStateOf("") }
    var apellidosMod by rememberSaveable { mutableStateOf("") }
    var telefonoMod by rememberSaveable { mutableStateOf("") }
    var dniBusqueda by rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            TopBar("Pacientes", nav, isDarkTheme, onToggleTheme)

            LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    SectionCard("Buscar", Icons.Filled.Search, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(dniBusqueda, { dniBusqueda = it }, Modifier.fillMaxWidth(), label = { Text("DNI") }, placeholder = { Text("Ej: 12345678") })
                        Spacer(Modifier.height(12.dp))
                        Button({ viewModel.buscar(dniBusqueda.trim()) }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("BUSCAR") }
                        viewModel.busqueda?.let { busqueda ->
                            Spacer(Modifier.height(12.dp))
                            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(10.dp)) {
                                Column(Modifier.fillMaxWidth().padding(14.dp)) {
                                    Text("Resultado de Búsqueda", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.height(4.dp))
                                    Text("${busqueda.nombres} ${busqueda.apellidos}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("DNI: ${busqueda.dni}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("Teléfono: ${busqueda.telefono}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("ID Interno: ${busqueda.id}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton({ viewModel.limpiarBusqueda(); dniBusqueda = "" }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("CERRAR") }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Registrar", Icons.Filled.Add, MaterialTheme.colorScheme.secondary) {
                        OutlinedTextField(dni, { dni = it }, Modifier.fillMaxWidth(), label = { Text("DNI") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(nombres, { nombres = it }, Modifier.fillMaxWidth(), label = { Text("Nombres") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(apellidos, { apellidos = it }, Modifier.fillMaxWidth(), label = { Text("Apellidos") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(telefono, { telefono = it }, Modifier.fillMaxWidth(), label = { Text("Teléfono") })
                        Spacer(Modifier.height(12.dp))
                        Button({
                            viewModel.agregar(Paciente(dni = dni.trim(), nombres = nombres.trim(), apellidos = apellidos.trim(), telefono = telefono.trim()))
                            dni = ""; nombres = ""; apellidos = ""; telefono = ""
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) { Text("REGISTRAR") }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Modificar", Icons.Filled.Edit, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(dniBusquedaMod, { dniBusquedaMod = it }, Modifier.fillMaxWidth(), label = { Text("⚠ DNI a modificar") }, placeholder = { Text("Ej: 12345678") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField( dniMod, { dniMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo DNI") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(nombresMod, { nombresMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos nombres") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(apellidosMod, { apellidosMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos apellidos") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(telefonoMod, { telefonoMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo teléfono") })
                        Spacer(Modifier.height(12.dp))
                        Button({
                            val pacienteAEditar = viewModel.pacientes.find { it.dni == dniBusquedaMod.trim() }
                            if (pacienteAEditar != null) {
                                viewModel.modificar(pacienteAEditar.id, Paciente(dni = dniMod.trim(), nombres = nombresMod.trim(), apellidos = apellidosMod.trim(), telefono = telefonoMod.trim()))
                                dniBusquedaMod = ""; dniMod = ""; nombresMod = ""; apellidosMod = ""; telefonoMod = ""
                            }
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("MODIFICAR") }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Listado (${viewModel.pacientes.size})", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                }

                items(viewModel.pacientes) { p ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("${p.nombres} ${p.apellidos}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("DNI: ${p.dni}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Teléfono: ${p.telefono}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("ID Interno: ${p.id}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                            }
                            IconButton({ viewModel.eliminar(p) }) { Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
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