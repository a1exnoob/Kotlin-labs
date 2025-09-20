package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WordSearchScreen()
                }
            }
        }
    }
}

val gtaMidnightBlue = Color(0xFF0A0A2A)

@Composable
fun WordSearchScreen() {
    val predefinedText = remember {
        "Apple Banana Level Radar Civic Rotator Test Text abba a"
    }
    var result by remember { mutableStateOf<List<String>?>(null) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Заголовок
        Text(
            text = "Анализатор текста",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = gtaMidnightBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Поиск слов с одинаковыми\nпервой и последней буквами",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Текст для анализа
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Текст для анализа:",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = predefinedText,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Кнопка поиска
        Button(
            onClick = {
                result = ListProcessor.findWordsWithSameStartEnd(predefinedText).words
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = gtaMidnightBlue,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Поиск",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Найти слова")
        }

        // Результаты
        if (result != null) {
            if (result!!.isEmpty()) {
                Text(
                    text = "Слова с одинаковыми первой и последней буквами не найдены",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Результаты поиска:",
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Text(
                            text = "Найдено слов: ${result!!.size}",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Divider(modifier = Modifier.padding(bottom = 12.dp))

                        Column {
                            result!!.forEachIndexed { index, word ->
                                Text(
                                    text = "${index + 1}. $word",
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}