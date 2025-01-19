package io.github.pavelannin.monadic.identifiable

import kotlinx.serialization.Serializable
import kotlin.native.ObjCName

/**
 * ###### EN:
 * The [Identifiable] type is a universal design designed for identification types.
 * Used to add a typed identifier [Id] to any type of [Wrapped].
 *
 * ###### RU:
 * Тип [Identifiable] представляет собой универсальную конструкцию, предназначенную для идентификации
 * типов. Используется для добавления типизированного идентификатора [Id] к любому типу [Wrapped].
 *
 * ###### Example:
 * ```
 * val userIdentifiable = Identifiable(id = 1, value = User(...))
 * ```
 */
@Serializable
@ObjCName("MonadicIdentifiable")
public data class Identifiable<Id, Wrapped>(
    public val id: Id,
    public val value: Wrapped,
)
