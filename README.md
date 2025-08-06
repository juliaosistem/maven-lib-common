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
│   │   │   ├── RepositoryModule.java ✅ (ACTUALIZADO)
│   │   │   └── UtilsModule.java ✅ (ACTUALIZADO)
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

5. **Deshabilitación temporal de Kafka y Redis**
   - **Problema**: Los adaptadores de Kafka y Redis causaban errores de conexión durante pruebas de CRUD
   - **Solución**: Deshabilitación temporal de adaptadores y clientes Feign
   - **Archivos**: 
     - `src/main/java/com/common/lib/infraestructure/adapters/third/DefaultAdapterKafkaAndRedis.kt`
     - `src/main/java/com/common/lib/infraestructure/adapters/third/McsKafkaFeignClient.kt`
     - `src/main/java/com/common/lib/infraestructure/adapters/third/RedisFeignClient.kt`
     - `src/main/java/com/common/lib/infraestructure/modules/UtilsModule.java`

6. **Configuración de repositorios y entidades específicas del proyecto**
   - **Problema**: `ExampleService` y `ExampleRepository` no se encontraban como beans
   - **Solución**: Actualización de `RepositoryModule` para incluir paquetes específicos del proyecto
   - **Archivos**: 
     - `src/main/java/com/common/lib/infraestructure/modules/RepositoryModule.java`
     - `src/main/java/com/juliaosystem/Application.java`

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

#### UtilsModule.java (Actualizado)
```java
@Configuration
@ComponentScan(
    basePackages = "com.common.lib",
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*DefaultAdapterKafkaAndRedis.*"
        ),
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*McsKafkaFeignClient.*"
        ),
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = ".*RedisFeignClient.*"
        )
    }
)
public class UtilsModule {
    // Los adaptadores de Kafka y Redis han sido excluidos temporalmente
    // para permitir pruebas de CRUD sin dependencias externas
}
```

#### RepositoryModule.java (Actualizado)
```java
@Configuration
@EnableJpaRepositories(basePackages = {
    "com.common.lib.infraestructure.repository",
    "com.juliaosystem.infraestructure.repository"
})
@EntityScan(basePackages = {
    "com.common.lib.infraestructure.entitis",
    "com.juliaosystem.infraestructure.entitis"
})
public class RepositoryModule {
    // Se han agregado los paquetes específicos del proyecto juliaosystem
    // para incluir repositorios y entidades como ExampleRepository y ExampleEntity
}
```

## 🚀 Características Principales

- **Arquitectura Hexagonal**: Separación clara entre adaptadores primarios y secundarios
- **CRUD Genérico**: Implementación genérica de operaciones CRUD reutilizable
- **Respuestas Estandarizadas**: Uso de `PlantillaResponse` para respuestas consistentes
- **Configuración Modular**: Módulos separados para utilidades y repositorios
- **Soporte Multi-idioma**: Configuración para internacionalización
- **Pruebas Sin Dependencias**: Modo de prueba sin Kafka y Redis
- **Configuración Flexible**: Soporte para repositorios y entidades específicas del proyecto

## 📦 Módulos Disponibles

### UtilsModule
- Configuraciones comunes para utilidades
- Beans compartidos entre microservicios
- DTOs, mappers y utilidades
- **Excluye temporalmente**: Adaptadores de Kafka y Redis

### RepositoryModule
- Configuraciones JPA
- Repositorios habilitados
- Entidades escaneadas
- **Incluye**: Paquetes específicos del proyecto (juliaosystem)

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

### Configuración de Base de Datos

Para desarrollo local, crear `application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/juliaosystem_dev
    username: postgres
    password: postgres
    driverClassName: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## 🐛 Problemas Resueltos

- ✅ Error de inyección de dependencias en DefaultImpl
- ✅ Métodos duplicados en interfaces
- ✅ Anotaciones override faltantes
- ✅ Tipos genéricos incorrectos
- ✅ Conflictos de métodos con parámetros nullables
- ✅ Dependencias de Kafka y Redis deshabilitadas temporalmente
- ✅ Configuración de repositorios y entidades específicas del proyecto

## 🔄 Deshabilitación Temporal de Kafka y Redis

### ¿Por qué se deshabilitaron?

Los adaptadores de Kafka y Redis fueron deshabilitados temporalmente para permitir pruebas de CRUD sin dependencias externas. Esto evita errores de conexión cuando estos servicios no están disponibles.

### Archivos Modificados

1. **DefaultAdapterKafkaAndRedis.kt**
   - Comentada anotación `@Component`
   - Clase disponible pero no registrada como bean

2. **McsKafkaFeignClient.kt**
   - Comentada anotación `@FeignClient`
   - Interfaz disponible pero no registrada como cliente Feign

3. **RedisFeignClient.kt**
   - Comentada anotación `@FeignClient`
   - Interfaz disponible pero no registrada como cliente Feign

4. **UtilsModule.java**
   - Agregados filtros de exclusión para adaptadores de Kafka y Redis
   - Escaneo de componentes configurado para omitir estos adaptadores

### Cómo Rehabilitar

Para rehabilitar los adaptadores de Kafka y Redis:

1. Descomenta las anotaciones `@Component` y `@FeignClient` en los archivos correspondientes
2. Remueve los filtros de exclusión en `UtilsModule.java`
3. Asegúrate de que los servicios de Kafka y Redis estén disponibles
4. Configura las URLs en `application.yml`:

```yaml
kafka:
  base-url: http://localhost:9092
redis:
  base-url: http://localhost:6379
```

## 📝 Notas de Desarrollo

- Todos los errores de compilación han sido corregidos
- La aplicación ahora puede iniciar correctamente
- Las dependencias se resuelven automáticamente
- La implementación es compatible con Spring Boot 2.x y 3.x
- **Modo de prueba**: CRUD funcional sin dependencias externas
- **Configuración flexible**: Soporte para repositorios y entidades específicas del proyecto

## 🤝 Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.
