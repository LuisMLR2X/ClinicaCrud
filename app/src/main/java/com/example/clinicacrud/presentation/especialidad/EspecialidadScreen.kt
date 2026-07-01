package com.example.clinicacrud.presentation.especialidad

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

@Composable
fun EspecialidadScreen(viewModel: EspecialidadViewModel, nav: NavController, isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var idMod by rememberSaveable { mutableStateOf("") }
    var nombreMod by rememberSaveable { mutableStateOf("") }
    var descripcionMod by rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            TopBar("Especialidades", nav, isDarkTheme, onToggleTheme)

            LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
                item {
                    SectionCard("Registrar", Icons.Filled.Add, MaterialTheme.colorScheme.primary) {
                        OutlinedTextField(nombre, { nombre = it }, Modifier.fillMaxWidth(), label = { Text("Nombre") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(descripcion, { descripcion = it }, Modifier.fillMaxWidth(), label = { Text("Descripción") })
                        Spacer(Modifier.height(12.dp))
                        Button({ viewModel.agregar(nombre.trim(), descripcion.trim()); nombre = ""; descripcion = "" }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) { Text("REGISTRAR") }
                    }
                    Spacer(Modifier.height(16.dp))
                    SectionCard("Modificar", Icons.Filled.Edit, MaterialTheme.colorScheme.secondary) {
                        OutlinedTextField(idMod, { idMod = it }, Modifier.fillMaxWidth(), label = { Text("⚠ ID a modificar") }, isError = idMod.isNotBlank() && idMod.toLongOrNull() == null)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(nombreMod, { nombreMod = it }, Modifier.fillMaxWidth(), label = { Text("Nuevo nombre") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(descripcionMod, { descripcionMod = it }, Modifier.fillMaxWidth(), label = { Text("Nueva descripción") })
                        Spacer(Modifier.height(12.dp))
                        Button({
                            viewModel.modificar(idMod.toLongOrNull() ?: 0, nombreMod.trim(), descripcionMod.trim())
                            idMod = ""; nombreMod = ""; descripcionMod = ""
                        }, Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) { Text("MODIFICAR") }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Listado (${viewModel.especialidades.size})", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                }

                items(viewModel.especialidades) { e ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("ID ${e.id} · ${e.nombre}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                Text(e.descripcion, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            }
                            IconButton({ viewModel.eliminar(e) }) { Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error) }
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
            IconButton(onToggleTheme) {
                Icon(if (isDarkTheme) Icons.Filled.LightMode else Icons.Filled.DarkMode, "Cambiar tema", tint = MaterialTheme.colorScheme.onPrimary)
            }
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