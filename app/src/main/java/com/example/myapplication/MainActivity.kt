package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.UserPreferences
import com.example.myapplication.ui.theme.FormViewModel
import com.example.myapplication.ui.theme.FormViewModelFactory
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val formViewModel: FormViewModel = viewModel(
                        factory = FormViewModelFactory(
                            owner = this@MainActivity,
                            userPrefs = UserPreferences(applicationContext)
                        )
                    )
                    ResilientFormScreen(viewModel = formViewModel)
                }
            }
        }
    }
}

@Composable
fun ResilientFormScreen(viewModel: FormViewModel) {
    val nameDisk by viewModel.nameFromDisk.observeAsState("")
    var nameInput by remember { mutableStateOf(nameDisk) }

    LaunchedEffect(nameDisk) {
        nameInput = nameDisk
    }
    // Implementación de Predictive Back (Navegación de Android 16)
    BackHandler(enabled = nameInput.isNotEmpty()) {
        // Lógica para interceptar el regreso si hay cambios sin guardar
        Log.d("NAV", "El usuario intentó retroceder con datos en el formulario")
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Borrador de Perfil", style = MaterialTheme.typography.headlineMedium)

        // Campo persistido en DISCO (DataStore)
        OutlinedTextField(
            value = nameInput,
            onValueChange = { it : String ->
                nameInput = it
                viewModel.saveName(it)
            },
            label = { Text("Nombre (Persiste al cerrar app)") }
        )

        if (viewModel.indicador) {
            Text(
                text = "✓ guardado en disco",
                style = MaterialTheme.typography.bodySmall

            )
        }

        // Campo persistido en MEMORIA/PROCESO (SavedStateHandle)
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email (Persiste al rotar/muerte proceso)") }
        )
    }
}

