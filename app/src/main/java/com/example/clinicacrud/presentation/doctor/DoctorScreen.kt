package com.example.clinicacrud.presentation.doctor

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
import com.example.clinicacrud.domain.model.Doctor
import com.example.clinicacrud.domain.model.Especialidad

@Composable
fun DoctorScreen(viewModel: DoctorViewModel, especialidades: List<Especialidad>, nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
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

    // Variable agregada para controlar el mensaje de error
    var errorRegistrar by rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            TopBar("Doctores", nav, isDarkTheme, onToggleTheme)

            LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    SectionCard("Buscar", Icons.Filled.Search, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(codigoBusqueda, { codigoBusqueda = it }, Modifier.fillMaxWidth(), label = { Text("Código") }, placeholder = { Text("Ej: CMP - 123456") })
                        Spacer(Modifier.height(12.dp))
                        Button({ viewModel.buscar(codigoBusqueda.trim()) }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("BUSCAR") }
                        viewModel.busqueda?.let { busqueda ->
                            val nombreEspecialidad = especialidades.find { it.id == busqueda.especialidadId }?.nombre ?: "No asignada"
                            Spacer(Modifier.height(12.dp))
                            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(10.dp)) {
                                Column(Modifier.fillMaxWidth().padding(14.dp)) {
                                    Text("Resultado de Búsqueda", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Spacer(Modifier.height(4.dp))
                                    Text("Dr(a). ${busqueda.nombres} ${busqueda.apellidos}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("Código: ${busqueda.codigo}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("Especialidad: $nombreEspecialidad", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    Text("Sexo: ${busqueda.sexo}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            OutlinedButton({ viewModel.limpiarBusqueda(); codigoBusqueda = "" }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("CERRAR") }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Registrar", Icons.Filled.Add, MaterialTheme.colorScheme.secondary) {
                        OutlinedTextField(
                            value = codigo,
                            onValueChange = { codigo = it; errorRegistrar = "" },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Código único") },
                            placeholder = { Text("Ej: CMP - 123456") }
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(nombres, { nombres = it }, Modifier.fillMaxWidth(), label = { Text("Nombres") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(apellidos, { apellidos = it }, Modifier.fillMaxWidth(), label = { Text("Apellidos") })
                        Spacer(Modifier.height(10.dp))
                        Text("Sexo", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(sexo == "M", { sexo = "M" }); Text("M", color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.width(16.dp))
                            RadioButton(sexo == "F", { sexo = "F" }); Text("F", color = MaterialTheme.colorScheme.onSurface)
                        }
                        OutlinedTextField(fechaNac, { fechaNac = it }, Modifier.fillMaxWidth(), label = { Text("Fecha nacimiento (dd/MM/aaaa)") })
                        Spacer(Modifier.height(10.dp))
                        Text("Especialidad", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        especialidades.forEach { e -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(especialidad == e.id, { especialidad = e.id }); Text(e.nombre, color = MaterialTheme.colorScheme.onSurface) } }

                        if (errorRegistrar.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            Text(errorRegistrar, color = MaterialTheme.colorScheme.error, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }

                        Spacer(Modifier.height(12.dp))
                        Button({
                            val codigoLimpio = codigo.trim()
                            val codigoExiste = viewModel.doctores.any { it.codigo == codigoLimpio }
                            val regexFormato = Regex("^CMP - \\d{6}\$")

                            if (codigoLimpio.isBlank()) {
                                errorRegistrar = "El código no puede estar vacío"
                            } else if (!codigoLimpio.matches(regexFormato)) {
                                errorRegistrar = "Formato inválido. Debe ser CMP - seguido de 6 números"
                            } else if (codigoExiste) {
                                errorRegistrar = "El código '$codigoLimpio' ya está registrado"
                            } else {
                                viewModel.agregar(Doctor(codigoLimpio, nombres.trim(), apellidos.trim(), sexo, fechaNac.trim(), especialidad))
                                codigo = ""; nombres = ""; apellidos = ""; fechaNac = ""
                                errorRegistrar = ""
                            }
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) { Text("REGISTRAR") }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Modificar", Icons.Filled.Edit, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(
                            value = codigoMod,
                            onValueChange = { codigoMod = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("⚠ Código a modificar") },
                            placeholder = { Text("Ej: CMP - 123456") }
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(nombresMod, { nombresMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos nombres") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(apellidosMod, { apellidosMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevos apellidos") })
                        Spacer(Modifier.height(10.dp))
                        Text("Sexo", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(sexoMod == "M", { sexoMod = "M" }); Text("M", color = MaterialTheme.colorScheme.onSurface)
                            Spacer(Modifier.width(16.dp))
                            RadioButton(sexoMod == "F", { sexoMod = "F" }); Text("F", color = MaterialTheme.colorScheme.onSurface)
                        }
                        OutlinedTextField(fechaNacMod, { fechaNacMod = it }, Modifier.fillMaxWidth(), label = { Text("Nueva fecha nacimiento") })
                        Spacer(Modifier.height(10.dp))
                        Text("Especialidad", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        especialidades.forEach { e -> Row(verticalAlignment = Alignment.CenterVertically) { RadioButton(especialidadMod == e.id, { especialidadMod = e.id }); Text(e.nombre, color = MaterialTheme.colorScheme.onSurface) } }
                        Spacer(Modifier.height(12.dp))
                        Button({
                            viewModel.modificar(Doctor(codigoMod.trim(), nombresMod.trim(), apellidosMod.trim(), sexoMod, fechaNacMod.trim(), especialidadMod))
                            codigoMod = ""; nombresMod = ""; apellidosMod = ""; fechaNacMod = ""
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("MODIFICAR") }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Listado (${viewModel.doctores.size})", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                }

                items(viewModel.doctores) { d ->
                    val nombreEspecialidad = especialidades.find { it.id == d.especialidadId }?.nombre ?: "No asignada"
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Dr(a). ${d.nombres} ${d.apellidos}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text("Código: ${d.codigo}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Especialidad: $nombreEspecialidad", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Sexo: ${d.sexo}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                            }
                            IconButton({ viewModel.eliminar(d) }) { Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Button({ nav.navigate("especialidades") }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("Ir a Especialidades") }
                    Spacer(Modifier.height(8.dp))
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