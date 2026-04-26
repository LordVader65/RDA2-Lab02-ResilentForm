## Comparación entre SavedStateHandle y DataStore

En esta práctica se utilizaron dos mecanismos para conservar información del formulario: `SavedStateHandle` y `DataStore`. Ambos permiten mantener datos, pero tienen propósitos diferentes dentro de una aplicación Android.

| Característica | SavedStateHandle | DataStore |
|---|---|---|
| Propósito | Guardar estado temporal de la pantalla | Guardar datos persistentes en el dispositivo |
| Tipo de persistencia | Estado asociado al `ViewModel` | Almacenamiento local en disco |
| Uso recomendado | Datos pequeños de la interfaz, como campos temporales | Preferencias del usuario o datos que deben mantenerse al cerrar la app |
| Ejemplo en la práctica | Campo `Email` | Campo `Nombre` |
| Sobrevive a rotación de pantalla | Sí | Sí |
| Sobrevive a recreación de la actividad | Sí | Sí |
| Sobrevive al cierre completo de la app | No es su objetivo principal | Sí |
| Forma de trabajo | Se usa junto con `ViewModel` | Se usa con `Flow` y corrutinas |
| Complejidad | Menor, ideal para estado simple | Mayor, pero más útil para persistencia real |

### Uso de SavedStateHandle en la práctica

`SavedStateHandle` se utilizó para conservar el valor del campo **Email**. Este mecanismo permite que el dato no se pierda cuando la pantalla se recrea, por ejemplo, al rotar el dispositivo o cuando Android destruye temporalmente la actividad.

```kotlin
var email by mutableStateOf(stateHandle.get<String>("email_key") ?: "")
    private set

fun updateEmail(newEmail: String) {
    email = newEmail
    stateHandle["email_key"] = newEmail
}
