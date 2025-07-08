package com.example.encuentramitelefono

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.encuentramitelefono.theme.EncuentraMiTelefonoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWithHistoryScreen()
        }
    }
}

@Composable
fun MainWithHistoryScreen() {
    var showHistory by remember { mutableStateOf(false) }
    if (showHistory) {
        HistoryScreenExample(onBack = { showHistory = false })
    } else {
        MainScreenExample(onShowHistory = { showHistory = true })
    }
}

@Composable
fun MainScreenExample(onShowHistory: () -> Unit = {}) {
    EncuentraMiTelefonoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3F2FD)), // Azul muy claro
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Encuentra tu teléfono",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "¿No encuentras tu móvil? Usa tu reloj para hacerlo sonar y vibrar.",
                fontSize = 18.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onShowHistory,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver historial de búsquedas", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun HistoryScreenExample(onBack: () -> Unit = {}) {
    EncuentraMiTelefonoTheme {
        val exampleHistory = listOf(
            "Búsqueda a las 01/06/2024 12:30:15",
            "Búsqueda a las 31/05/2024 18:45:02",
            "Búsqueda a las 30/05/2024 09:10:55"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF1F8E9)), // Fondo verde muy claro
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Historial de búsquedas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF388E3C),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(exampleHistory) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color(0xFF388E3C),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(item, fontSize = 16.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp)
            ) {
                Text("Volver", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    MainScreenExample()
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHistoryScreen() {
    HistoryScreenExample()
}