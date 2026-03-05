package com.papillon.widget.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papillon.widget.network.PapillonBackend
import com.papillon.widget.network.SchoolResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolScreen(onSchoolSelected: (String, String) -> Unit, onBack: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var manualUrl by remember { mutableStateOf("") }
    var schools by remember { mutableStateOf(listOf<SchoolResult>()) }
    var isLoading by remember { mutableStateOf(false) }
    val backend = remember { PapillonBackend() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
        ) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "Retour")
        }

        Text(
            text = "Trouvez votre école",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Recherchez votre établissement ou entrez son URL directement.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        // Recherche par nom
        OutlinedTextField(
            value = query,
            onValueChange = { 
                query = it
                if (it.length > 2) {
                    scope.launch {
                        isLoading = true
                        schools = backend.searchSchools(it)
                        isLoading = false
                    }
                }
            },
            label = { Text("Nom de l'établissement") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        // URL Manuelle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = manualUrl,
                onValueChange = { manualUrl = it },
                label = { Text("URL PRONOTE") },
                placeholder = { Text("https://0000000x.index-education.net/pronote/") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) },
                singleLine = true
            )
            
            Button(
                onClick = { 
                    if (manualUrl.isNotBlank()) {
                        onSchoolSelected("URL Manuelle", manualUrl.trim())
                    }
                },
                enabled = manualUrl.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(56.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Valider URL")
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.5f))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(schools) { school ->
                Card(
                    onClick = { onSchoolSelected(school.name, school.url) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = school.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = school.url, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                    }
                }
            }
            
            if (schools.isEmpty() && query.length > 2 && !isLoading) {
                item {
                    Text(
                        text = "Aucun établissement trouvé pour \"$query\". Essayez l'URL manuelle.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
