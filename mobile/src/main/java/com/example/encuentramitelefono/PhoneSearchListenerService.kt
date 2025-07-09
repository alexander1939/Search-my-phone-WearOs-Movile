package com.example.encuentramitelefono

import android.content.Intent
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class PhoneSearchListenerService : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == "/buscar-telefono") {
            // Lanzar la actividad principal y activar la alerta
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra("buscar_telefono", true)
            }
            startActivity(intent)
        } else if (messageEvent.path == "/detener-busqueda") {
            // Lanzar la actividad principal y detener la alerta
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra("detener_busqueda", true)
            }
            startActivity(intent)
        }
    }
} 