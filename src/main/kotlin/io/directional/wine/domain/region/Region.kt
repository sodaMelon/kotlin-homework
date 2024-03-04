package io.directional.wine.domain.region

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "region")
class Region(
        var name_korean: String,
        var name_english: String,
        var parent_name_korean: String?,
        var parent_name_english: String?

) : BaseEntity(){
   fun update(dto: Region){
       this.name_korean = dto.name_korean
       this.name_english = dto.name_english
       this.parent_name_korean = dto.parent_name_korean
       this.parent_name_english = dto.name_english
   }
}