# ⚖️ LeoLey

Aplicación Android para consultar leyes por su número y guardarlas como marcadores, desarrollada con **Kotlin** y **Jetpack Compose**.

> [!NOTE]
> Este proyecto se encuentra **en desarrollo**. Algunas pantallas y funciones descritas aquí pueden estar incompletas.

## Creadores

- [Arion Ruiz-Tagle](https://github.com/kawyyz)
- [Gustavo Mendez](https://github.com/Gustavomendezcrts)

## Funcionalidades

| Pantalla | Descripción |
| --- | --- |
| **Login** | Inicio de sesión con usuario y contraseña |
| **Bienvenida** | Menú principal con acceso a Marcadores, Ayuda y Buscador |
| **Marcadores** | Ver y eliminar las leyes guardadas |
| **Ayuda** | Enviar un mensaje a los desarrolladores |
| **Buscador** | Buscar una ley por su número en una API y agregarla a marcadores |

> [!IMPORTANT]
> Por ahora el login funciona **sin persistencia** y con un único usuario de prueba.

## Tecnologías

- Kotlin
- Jetpack Compose
- Arquitectura MVVM
- Gradle (Kotlin DSL)
- Android Studio

## Estructura del proyecto

El código vive en el paquete `cl.duoc.leoley`:

```text
app/src/main/java/cl/duoc/leoley/
├── MainActivity.kt   # Solo inicia la app, sin UI
├── ui/               # Pantallas (Login, Marcadores, Ayuda, Buscador...)
├── viewmodel/        # ViewModels
├── repository/       # Acceso a datos
└── model/            # Modelos de datos
```

> [!TIP]
> En la raíz del paquete solo debe estar `MainActivity`. Las pantallas van en `ui`, la lógica de estado en `viewmodel` y los datos en `repository` y `model`.
