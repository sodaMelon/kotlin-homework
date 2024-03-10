package io.directional.wine.domain.region

import io.directional.wine.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "region")
class Region(
        var nameKorean: String,
        var nameEnglish: String,
        var parentNameKorean: String?,
        var parentNameEnglish: String?

) : BaseEntity(){
   fun update(dto: Region){
       this.nameKorean = dto.nameKorean
       this.nameEnglish = dto.nameEnglish
       this.parentNameKorean = dto.parentNameKorean
       this.parentNameEnglish = dto.nameEnglish
   }
}