package com.example.encuentramitelefono

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.encuentramitelefono.theme.EncuentraMiTelefonoTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWithHistoryAndAlertScreen()
        }
    }
}

@Composable
fun MainWithHistoryAndAlertScreen() {
    var showHistory by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    when {
        showAlert -> AlertScreenExample(onStop = { showAlert = false })
        showHistory -> HistoryScreenExample(onBack = { showHistory = false })
        else -> MainScreenExample(
            onShowHistory = { showHistory = true },
            onShowAlert = { showAlert = true }
        )
    }
}

@Composable
fun MainScreenExample(onShowHistory: () -> Unit = {}, onShowAlert: () -> Unit = {}) {
    EncuentraMiTelefonoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFe0eafc), Color(0xFFcfdef3))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Encuentra tu teléfono",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1976D2),
                    modifier = Modifier.shadow(2.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(Color(0xFF1976D2), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "¿No encuentras tu móvil? Usa tu reloj para hacerlo sonar y vibrar.",
                    fontSize = 18.sp,
                    color = Color(0xFF374151),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = onShowAlert,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1976D2))
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hacer prueba de búsqueda", fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(18.dp))
                OutlinedButton(
                    onClick = onShowHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver historial de búsquedas", fontSize = 18.sp, color = Color(0xFF1976D2))
                }
            }
        }
    }
}

@Composable
fun AlertScreenExample(onStop: () -> Unit = {}) {
    val context = LocalContext.current
    // --- Vibración y sonido ---
    LaunchedEffect(Unit) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(longArrayOf(0, 500, 500, 500), 0)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 500, 500, 500), 0)
        }
    }
    var ringtone: Ringtone? = null
    DisposableEffect(Unit) {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, uri)
        ringtone?.play()
        onDispose {
            ringtone?.stop()
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.cancel()
        }
    }
    // --- UI visual ---
    EncuentraMiTelefonoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF5252), Color(0xFFFFC107))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Alarma",
                        tint = Color(0xFFFF5252),
                        modifier = Modifier.size(90.dp)
                    )
                }
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "¡Aquí estoy!",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.shadow(4.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Tu móvil está sonando y vibrando para que lo encuentres.",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = onStop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text("Detener", fontSize = 22.sp, color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
                }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF1F8E9), Color(0xFFE0F2F1))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
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
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(20.dp),
                            elevation = 6.dp,
                            backgroundColor = Color.White
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(0xFF388E3C), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(item, fontSize = 16.sp, color = Color(0xFF374151))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                ) {
                    Text("Volver", fontSize = 16.sp, color = Color(0xFF388E3C))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
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
@Preview(showSystemUi = true)
@Composable
fun PreviewAlertScreen() {
    AlertScreenExample()
}