package io.directional.wine.domain.aroma

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity

@Entity
class Aroma(
    val name: String,
) : BaseEntity()
