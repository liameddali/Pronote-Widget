package com.papillon.widget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papillon.widget.Lesson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    // Simulation de données
    val lessons = remember {
        listOf(
            Lesson("1", "MATHEMATIQUES", "M. DUPONT", "B201", LocalDateTime.now().withHour(8).withMinute(0), LocalDateTime.now().withHour(9).withMinute(30), 0xFF3B82F6.toInt()),
            Lesson("2", "HISTOIRE-GEO", "MME MARTIN", "A102", LocalDateTime.now().withHour(10).withMinute(0), LocalDateTime.now().withHour(11).withMinute(0), 0xFF10B981.toInt()),
            Lesson("3", "PHYSIQUE-CHIMIE", "M. BERNARD", "LABO 1", LocalDateTime.now().withHour(13).withMinute(30), LocalDateTime.now().withHour(15).withMinute(30), 0xFFBE185D.toInt())
        )
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Emploi du temps",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 32.sp
                        )
                        Text(
                            text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRANCE)).uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray,
                            letterSpacing = 2.sp
                        )
                    }
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { /* Mode Widget */ },
                            modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Smartphone, contentDescription = null, tint = Color(0xFF2563EB))
                        }
                        IconButton(
                            onClick = { /* Recherche */ },
                            modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF2563EB).copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("JD", fontWeight = FontWeight.Black, color = Color(0xFF2563EB))
                        }
                        Column {
                            Text("John Doe", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("TERMINALE A", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold, fontSize = 10.sp)
                        }
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Déconnexion", tint = Color.Gray)
                    }
                }
            }
        },
        containerColor = Color(0xFFF8FAFC)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(lessons) { lesson ->
                LessonCard(lesson)
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Papillon Ecosystem • 2026",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.LightGray,
                        letterSpacing = 4.sp
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(Color(lesson.color), RoundedCornerShape(2.dp))
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.subject,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Salle ${lesson.room} • ${lesson.teacher}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = lesson.start.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lesson.end.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
