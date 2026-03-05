package com.papillon.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.background
import androidx.glance.appwidget.cornerRadius
import androidx.compose.ui.graphics.Color
import androidx.glance.text.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Widget Android natif pour Papillon
 * Utilise Jetpack Glance pour une interface moderne et réactive.
 */
class TimetableWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent()
        }
    }

    @Composable
    private fun WidgetContent() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White)
                .cornerRadius(24.dp)
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text(
                        text = "AUJOURD'HUI",
                        style = TextStyle(
                            color = ColorProvider(Color(0xFF3B82F6)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }
                Text(
                    text = "15:42",
                    style = TextStyle(
                        color = ColorProvider(Color(0xFF9CA3AF)),
                        fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = GlanceModifier.height(12.dp))

            // Liste des cours (Exemple de ligne)
            LessonItem(
                subject = "HISTOIRE-GEOGRAPHIE",
                time = "09:00 - 10:00",
                room = "E2-02",
                color = Color(0xFF3B82F6)
            )
            
            LessonItem(
                subject = "ANGLAIS",
                time = "10:00 - 11:00",
                room = "C3-05",
                color = Color(0xFF10B981)
            )

            // Cours actuel (mis en avant)
            CurrentLessonItem(
                subject = "SPE SPC",
                time = "16:00 - 18:00",
                room = "C1-06 Physique",
                color = Color(0xFFBE185D)
            )
        }
    }

    @Composable
    private fun LessonItem(subject: String, time: String, room: String, color: Color) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Box(
                modifier = GlanceModifier
                    .width(4.dp)
                    .height(32.dp)
                    .background(color)
                    .cornerRadius(2.dp)
            ) {}
            
            Spacer(modifier = GlanceModifier.width(12.dp))
            
            Column {
                Text(
                    text = subject,
                    style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
                )
                Text(
                    text = " Salle $room • $time",
                    style = TextStyle(color = ColorProvider(Color(0xFF6B7280)), fontSize = 11.sp)
                )
            }
        }
    }

    @Composable
    private fun CurrentLessonItem(subject: String, time: String, room: String, color: Color) {
        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .background(Color(0xFFF3F4F6))
                .cornerRadius(16.dp)
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                Box(
                    modifier = GlanceModifier
                        .width(4.dp)
                        .height(24.dp)
                        .background(color)
                        .cornerRadius(2.dp)
                ) {}
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(
                    text = subject,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
                )
            }
            Text(
                text = "En cours • $room",
                style = TextStyle(color = ColorProvider(color), fontSize = 12.sp)
            )
        }
    }
}
