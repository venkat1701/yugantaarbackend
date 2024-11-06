package io.github.venkat1701.yugantaarbackend.services.core.commons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * The `GenericCrudService` interface provides a common set of CRUD (Create, Read, Update, Delete) operations
 * for working with entities in a Spring Boot application.
 *
 * @param <T> the type of the entity
 * @param <ID> the type of the entity's identifier
 *
 * @author Venkat
 * @since 1.0
 */
public interface GenericCrudService<T, ID> {
    /**
     * Retrieves a paginated list of entities based on the provided `PageRequest`.
     *
     * @param pageRequest the `PageRequest` object containing the pagination and sorting information
     * @return a `Page` of entities
     */
    Page<T> search(PageRequest pageRequest);

    /**
     * Retrieves a list of all entities.
     *
     * @return a list of all entities
     */
    List<T> getAll();

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id the identifier of the entity
     * @return an `Optional` containing the entity if found, or an empty `Optional` if not found
     */
    Optional<T> findById(ID id);

    /**
     * Updates an existing entity with the provided data.
     *
     * @param id the identifier of the entity to update
     * @param user the updated entity data
     * @return an `Optional` containing the updated entity if successful, or an empty `Optional` if the entity was not found
     */
    Optional<T> update(ID id, T user);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity to delete
     * @return `true` if the entity was deleted, `false` if the entity was not found
     */
    boolean delete(ID id);
}