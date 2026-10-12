package com.example.jusalpukat // Sesuaikan dengan nama package Anda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jusalpukat.ui.theme.JusAlpukatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JusAlpukatTheme {
                AlpukatApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlpukatApp() {
    var step by remember { mutableStateOf(1) }
    var alpukatClicks by remember { mutableStateOf(0) }
    var drinkClicks by remember { mutableStateOf(0) } // Status jumlah ceguk/minum

    // Target klik acak untuk membuat jus (Tahap 2) dan minum (Tahap 3)
    var targetClicks by remember { mutableStateOf((2..5).random()) }
    var targetDrinkClicks by remember { mutableStateOf((2..5).random()) }

    val imageResource = when (step) {
        1 -> R.drawable.pohonalpukat2
        2 -> R.drawable.alpukat
        3 -> R.drawable.jusalpukat2
        4 -> R.drawable.gelas2
        else -> R.drawable.pohonalpukat2
    }

    // Teks menyesuaikan dengan status klik masing-masing tahap
    val stepText = when (step) {
        1 -> "Tahap 1: Pohon Alpukat\n(Ketuk gambar untuk memetik)"
        2 -> "Tahap 2: Buah Alpukat\n(Ketuk gambar untuk membuat jus: $alpukatClicks/$targetClicks)"
        3 -> "Tahap 3: Jus Alpukat\n(Ketuk gambar untuk meminum: $drinkClicks/$targetDrinkClicks)"
        4 -> "Tahap 4: Gelas Kosong\n(Ketuk gambar untuk menanam ulang)"
        else -> ""
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Jus Alpukat Interactive",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF8BC34A)) // Warna background
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Proses membuat jus alpukat",
                modifier = Modifier
                    .size(280.dp)
                    .clickable {
                        when (step) {
                            1 -> step = 2
                            2 -> {
                                alpukatClicks++
                                if (alpukatClicks >= targetClicks) {
                                    step = 3
                                    alpukatClicks = 0
                                    targetClicks = (2..9).random() // Reset target buat jus selanjutnya
                                }
                            }

                            //proses klik saat minum
                            3 -> {
                                drinkClicks++
                                if (drinkClicks >= targetDrinkClicks) {
                                    step = 4
                                    drinkClicks = 0
                                    targetDrinkClicks = (2..5).random() // Reset target minum selanjutnya
                                }
                            }
                            4 -> step = 1
                        }
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stepText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlpukatAppPreview() {
    JusAlpukatTheme {
        AlpukatApp()
    }
}