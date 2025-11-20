# Maven Lib Common
## Uso como dependencia

Instala el jar localmente y úsalo desde tus micros:

```bash
mvn -DskipTests install
```

En el microservicio (`mcs-plantilla-spring`), añade:

```xml
<dependency>
  <groupId>common-lib</groupId>
  <artifactId>common-lib</artifactId>
  <version>0.0.0-develop</version>
</dependency>
```

Si deseas heredar versiones y plugins, usa el parent `common-parent` incluido en `common-parent/pom.xml` y referencia como `<parent>` en tus micros.

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


## 🚀 Características Principales

- **Arquitectura Hexagonal**: Separación clara entre adaptadores primarios y secundarios
- **CRUD Genérico**: Implementación genérica de operaciones CRUD reutilizable
- **Respuestas Estandarizadas**: Uso de `PlantillaResponse` para respuestas consistentes
- **Configuración Modular**: Módulos separados para utilidades y repositorios
- **Soporte Multi-idioma**: Configuración para internacionalización
- **Pruebas Sin Dependencias**: Modo de prueba sin Kafka y Redis
- **Configuración Flexible**: Soporte para repositorios y entidades específicas del proyecto



### Importar en otros microservicios

```java
@Import({UtilsModule.class, RepositoryModule.class})
@SpringBootApplication
public class YourApplication {
    // ...
}
```

