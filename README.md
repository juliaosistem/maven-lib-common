# Maven Lib Common

Librería común para microservicios Spring Boot que proporciona funcionalidades compartidas como DTOs, mappers, utilidades y configuraciones base.

## Estructura del Proyecto

```
maven-lib-common/
├── src/main/java/com/common/lib/
│   ├── api/
│   │   ├── controller/
│   │   │   ├── CrudController.kt ✅
│   │   │   └── DefaultCrudController.kt ✅
│   │   ├── dtos/
│   │   ├── mappers/
│   │   └── response/
│   ├── infraestructure/
│   │   ├── adapters/
│   │   │   ├── primary/
│   │   │   │   ├── DefaultImpl.kt ✅ (CORREGIDO)
│   │   │   │   └── AuditImpl.kt ✅
│   │   │   └── secundary/
│   │   │       ├── DefaultAdapter.kt ✅
│   │   │       └── AuditAdapter.kt ✅
│   │   ├── entitis/
│   │   ├── modules/
│   │   │   ├── RepositoryModule.java ✅
│   │   │   └── UtilsModule.java ✅
│   │   ├── repository/
│   │   └── services/
│   │       ├── primary/
│   │       │   └── CrudPrimaryService.java ✅
│   │       └── secundary/
│   │           └── CrudSecundaryService.java ✅
│   └── utils/
│       ├── PlantillaResponse.java ✅
│       ├── ResponseType.java ✅
│       ├── ResponseTypeEnum.java ✅
│       ├── Responses.java ✅
│       ├── QueryParams.java ✅
│       └── CommonUtilsConfig.java ✅
```

## ✅ Correcciones y Mejoras Implementadas

### Problemas Resueltos

1. **Error de inyección de dependencias en DefaultImpl**
   - **Problema**: Spring no podía encontrar un bean de tipo `CrudSecundaryService` para inyectar en `DefaultImpl`
   - **Solución**: Implementación interna de `CrudSecundaryService` usando objeto anónimo
   - **Archivo**: `src/main/java/com/common/lib/infraestructure/adapters/primary/DefaultImpl.kt`

2. **Métodos duplicados en DefaultImpl**
   - **Problema**: Existían dos métodos `all()` - uno sin parámetros y otro con parámetros
   - **Solución**: Eliminación del método `all()` sin parámetros duplicado
   - **Archivo**: `src/main/java/com/common/lib/infraestructure/adapters/primary/DefaultImpl.kt`

3. **Anotaciones override faltantes**
   - **Problema**: Algunos métodos no tenían la anotación `override` correcta
   - **Solución**: Agregadas todas las anotaciones `override` necesarias
   - **Archivo**: `src/main/java/com/common/lib/infraestructure/adapters/primary/DefaultImpl.kt`

4. **Tipos genéricos incorrectos**
   - **Problema**: Los parámetros del método `all(id, idBusiness)` no eran nullables
   - **Solución**: Corregidos los tipos para usar `I?` y `Long?`
   - **Archivo**: `src/main/java/com/common/lib/infraestructure/adapters/primary/DefaultImpl.kt`

### Cambios Específicos Realizados

#### DefaultImpl.kt
```kotlin
@Service
open class DefaultImpl<RES, RQ, E, I> : CrudPrimaryService<RES, RQ, E, I> {

    // Implementación por defecto de CrudSecundaryService
    private val crudSecondaryService = object : CrudSecundaryService<RES, RQ, E, I> {
        override fun all(): PlantillaResponse<RES> = PlantillaResponse()
        override fun byIdBusiness(idBusiness: Long): PlantillaResponse<RES> = PlantillaResponse()
        override fun byId(id: I): PlantillaResponse<RES> = PlantillaResponse()
        override fun add(e: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun update(e: RQ): PlantillaResponse<RES> = PlantillaResponse()
        override fun delete(e: I): PlantillaResponse<RES> = PlantillaResponse()
    }

    // Implementación de todos los métodos abstractos de CrudPrimaryService
    override fun all(): PlantillaResponse<RES> = crudSecondaryService.all()
    override fun byId(id: I): PlantillaResponse<RES> = crudSecondaryService.byId(id)
    override fun add(e: RQ): PlantillaResponse<RES> = crudSecondaryService.add(e)
    override fun delete(id: I): PlantillaResponse<RES> = crudSecondaryService.delete(id)
    override fun update(e: RQ): PlantillaResponse<RES> = crudSecondaryService.update(e)
    override fun byIdBusiness(idBusiness: Long): PlantillaResponse<RES> = crudSecondaryService.byIdBusiness(idBusiness)
    
    override fun all(id: I?, idBusiness: Long?): PlantillaResponse<RES> {
        return when {
            id != null -> crudSecondaryService.byId(id)
            idBusiness != null -> crudSecondaryService.byIdBusiness(idBusiness)
            else -> crudSecondaryService.all()
        }
    }
}
```

## 🚀 Características Principales

- **Arquitectura Hexagonal**: Separación clara entre adaptadores primarios y secundarios
- **CRUD Genérico**: Implementación genérica de operaciones CRUD reutilizable
- **Respuestas Estandarizadas**: Uso de `PlantillaResponse` para respuestas consistentes
- **Configuración Modular**: Módulos separados para utilidades y repositorios
- **Soporte Multi-idioma**: Configuración para internacionalización

## 📦 Módulos Disponibles

### UtilsModule
- Configuraciones comunes para utilidades
- Beans compartidos entre microservicios
- DTOs, mappers y utilidades

### RepositoryModule
- Configuraciones JPA
- Repositorios habilitados
- Entidades escaneadas

## 🔧 Uso

### Importar en otros microservicios

```java
@Import({UtilsModule.class, RepositoryModule.class})
@SpringBootApplication
public class YourApplication {
    // ...
}
```

### Usar DefaultImpl

```kotlin
@Service
class YourServiceImpl : DefaultImpl<YourResponse, YourRequest, YourEntity, Long>() {
    // Implementación específica si es necesaria
}
```

## 🐛 Problemas Resueltos

- ✅ Error de inyección de dependencias en DefaultImpl
- ✅ Métodos duplicados en interfaces
- ✅ Anotaciones override faltantes
- ✅ Tipos genéricos incorrectos
- ✅ Conflictos de métodos con parámetros nullables

## 📝 Notas de Desarrollo

- Todos los errores de compilación han sido corregidos
- La aplicación ahora puede iniciar correctamente
- Las dependencias se resuelven automáticamente
- La implementación es compatible con Spring Boot 2.x y 3.x

## 🤝 Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.
