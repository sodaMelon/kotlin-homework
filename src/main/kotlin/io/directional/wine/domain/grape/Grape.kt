package io.directional.wine.domain.grape

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "grape")
data class Grape(
        var nameKorean: String,
        var nameEnglish: String,
        var acidity: Int,
        var body: Int,
        var sweetness: Int,
        var tannin: Int
) : BaseEntity()