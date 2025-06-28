package com.common.lib.infraestructure.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort


@NoRepositoryBean
interface DefaultRepository<E, I> : JpaRepository<E, I>, JpaSpecificationExecutor<E> {

    /**
     * Método que devuelve una lista de entidades paginada, con la opción de filtrar por `id` e `idBusiness`.
     * Si no se proporcionan estos filtros, se devuelven todos los elementos paginados.
     * También permite especificar el campo por el que se ordena y la dirección del orden.
     *
     * @param id Opcional. Filtro por el identificador de la entidad.
     * @param idBusiness Opcional. Filtro por el identificador del negocio.
     * @param pagina El número de la página a devolver. El valor predeterminado es 0 (página 0).
     * @param tamanoPagina El tamaño de la página (número de elementos por página). El valor predeterminado es 100.
     * @param sortBy El nombre del campo por el cual se debe ordenar los resultados. El valor predeterminado es "id".
     * @param sortOrder La dirección del orden (ascendente o descendente). El valor predeterminado es ascendente.
     *
     * @return Un objeto `Page<E>` que contiene los resultados paginados.
     */
    fun All(
        id: I? = null,
        idBusiness: Long? = null,          // idBusiness opcional para filtrar por el negocio
        pagina: Int = 0,                  // Página por defecto es la 0
        tamanoPagina: Int = 100,          // Tamaño de página por defecto es 100
        sortBy: String = "id",            // Ordenar por el campo "id" por defecto
        sortOrder: Sort.Direction = Sort.Direction.ASC // Orden ascendente por defecto
    ): Page<E> {
        val pageable: Pageable = PageRequest.of(pagina, tamanoPagina, Sort.by(sortOrder, sortBy))

        return if (id != null && idBusiness != null) {
            findById(id, pageable)
        } else if (id != null) {
            findById(id, pageable)
        } else if (idBusiness != null) {
            findByIdBusiness(idBusiness, pageable)
        } else {
            findAll(pageable)
        }
    }

    /**
     * Método que obtiene los elementos filtrados por `id` con paginación.
     *
     * @param id El identificador de la entidad a buscar.
     * @param pageable El objeto `Pageable` que contiene la información de la página y el orden.
     *
     * @return Un objeto `Page<E>` que contiene los resultados filtrados por `id` y paginados.
     */
    fun findById(id: I, pageable: Pageable): Page<E>

    /**
     * Método que obtiene los elementos filtrados por `idBusiness` con paginación.
     *
     * @param idBusiness El identificador del negocio para filtrar los resultados.
     * @param pageable El objeto `Pageable` que contiene la información de la página y el orden.
     *
     * @return Un objeto `Page<E>` que contiene los resultados filtrados por `idBusiness` y paginados.
     */
    fun findByIdBusiness(idBusiness: Long, pageable: Pageable): Page<E>
}
