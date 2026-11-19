# ⚖️ LeoLey

Aplicación Android para consultar leyes por su número o nombre, guardarlas como marcadores y descargarlas en PDF, desarrollada con **Kotlin** y **Jetpack Compose**.

> [!NOTE]
> Este proyecto se encuentra **en desarrollo**. Algunas pantallas y funciones descritas aquí pueden cambiar.

## Creadores

- [Arion Ruiz-Tagle](https://github.com/kawyyz)
- [Gustavo Mendez](https://github.com/Gustavomendezcrts)

## Funcionalidades

| Pantalla | Descripción |
| --- | --- |
| **Login** | Inicio de sesión con correo y contraseña. La sesión queda guardada, así que al reabrir la app entras directo |
| **Registro** | Crear una cuenta nueva (nombre, correo y contraseña de al menos 8 caracteres) |
| **Dashboard** | Menú principal con saludo (`Hola <nombre>`) y acceso a Marcadores, Ayuda y Buscador. Desde aquí se cierra sesión |
| **Buscador** | Busca leyes por nombre o número en una API y permite guardarlas como marcador |
| **Marcadores** | Ver y eliminar las leyes guardadas, y **descargar cada ley en PDF** |
| **Ayuda** | Formulario para escribir a los desarrolladores; abre la app de correo del teléfono con el mensaje listo |

### Descarga de PDF y notificación

Desde **Marcadores**, el botón **Descargar** genera un PDF de la ley (ej. `Ley_19628.pdf`) y lo guarda en la carpeta **Descargas** del teléfono. Al terminar aparece una notificación de **"Descarga exitosa"**, como en los navegadores.

> [!IMPORTANT]
> - La descarga de PDF requiere **Android 10 o superior**.
> - En **Android 13 o superior** la app pide permiso para mostrar notificaciones al entrar a Marcadores. Si se rechaza, el PDF se descarga igual, pero no se ve el aviso.

## Tecnologías

- Kotlin
- Jetpack Compose (Material 3) y Navigation Compose
- Arquitectura MVVM
- Retrofit + Gson para consumir la API (leyes y autenticación)
- [Xano](https://www.xano.com/) como backend (API y base de datos de usuarios y leyes)
- Room para guardar los marcadores en el teléfono
- SharedPreferences para recordar la sesión
- JUnit para las pruebas unitarias
- Gradle (Kotlin DSL)
- Android Studio

## Backend

Para el backend usamos **Xano**, que nos permitió tener la API de leyes y la autenticación de usuarios sin programar un servidor propio.

> [!NOTE]
> Xano **no es un requisito**: la app solo necesita una API que responda como se describe abajo, así que el backend se puede hacer con cualquier otra tecnología (Spring Boot, Node.js, Django, Firebase, etc.).

### Qué espera la app de la API

| Endpoint | Método | Envía | Devuelve |
| --- | --- | --- | --- |
| `auth/login` | POST | `{ "email", "password" }` | `{ "authToken" }` |
| `auth/signup` | POST | `{ "name", "email", "password" }` | `{ "authToken" }` |
| `auth/me` | GET | Header `Authorization: Bearer <token>` | `{ "id", "name", "email" }` |
| `leyes?busqueda=<texto>` | GET | Parámetro `busqueda` (si va vacío, devuelve todas) | Lista de `{ "numero", "nombre", "descripcion", "anio" }` |

Además, ante credenciales incorrectas el login debe responder con un código de error **4xx** (la app lo muestra como "Correo o contraseña incorrectos").

### Cómo cambiar de backend

1. Monta tu API con los endpoints y formatos de la tabla anterior.
2. En `data/RetrofitClient.kt`, cambia las URLs base (`URL_LEYES` y `URL_AUTH`) por las de tu servidor.
3. Si tus rutas o nombres de campos son distintos, ajústalos en `data/AuthApiService.kt`, `data/LeyApiService.kt` y en los modelos de `model/` (con `@SerializedName`).

## Requisitos

- Android Studio (versión reciente)
- Android 7.0 (API 24) o superior para ejecutar la app
- Conexión a internet (login, registro y buscador usan la API del backend)

## Cómo ejecutarlo

1. Clona el repositorio:

   ```bash
   git clone https://github.com/Gustavomendezcrts/DesarrolloMobile.git
   ```

2. Abre la carpeta `DesarrolloMobile` con Android Studio y espera a que termine la sincronización de Gradle.
3. Elige un emulador o conecta un teléfono y presiona **Run ▶**.

## Estructura del proyecto

El código vive en el paquete `cl.duoc.leoley`:

```text
app/src/main/java/cl/duoc/leoley/
├── MainActivity.kt   # Solo inicia la app, sin UI
├── ui/               # Pantallas (Login, Registro, Dashboard, Marcadores, Ayuda, Buscador...)
├── viewmodel/        # ViewModels (estado de cada pantalla)
├── repository/       # Lógica de acceso a datos (Auth, Buscador, Marcadores, Sesión, PDF)
├── data/             # Retrofit (API) y Room (base de datos local)
└── model/            # Modelos de datos (Ley, Usuario, peticiones de login/registro)
```

> [!TIP]
> En la raíz del paquete solo debe estar `MainActivity`. Las pantallas van en `ui`, la lógica de estado en `viewmodel`, y los datos en `repository`, `data` y `model`.

## Pruebas unitarias

Las pruebas están en `app/src/test/java/cl/duoc/leoley/` y llaman a la **API real**, por lo que necesitan internet.

| Archivo | Prueba | Resultado esperado |
| --- | --- | --- |
| `BuscadorRepositoryTest` | `buscar_ley_correcta` | ✅ Pasa: la API devuelve la ley esperada |
| `BuscadorRepositoryTest` | `buscar_ley_error` | ❌ Falla a propósito: espera un número de ley incorrecto |
| `AuthRepositoryTest` | `login_correcto` | ✅ Pasa: usuario de prueba válido, la API entrega un token |
| `AuthRepositoryTest` | `login_error` | ❌ Falla a propósito: clave incorrecta, la API rechaza el login |

Para ejecutarlas en Android Studio: clic derecho sobre el archivo de prueba → **Run**. También se puede usar `./gradlew test` desde la terminal.

> [!NOTE]
> Los tests que "deben fallar" existen para mostrar cómo se ve una prueba con error. Cada test imprime en la consola lo que hizo, lo que esperaba y lo que llegó.