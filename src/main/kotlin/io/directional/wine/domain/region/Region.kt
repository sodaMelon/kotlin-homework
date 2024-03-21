package io.directional.wine.domain.region


import io.directional.wine.domain.region.dto.CreateRegionDto
import io.directional.wine.domain.region.dto.UpdateRegionDto
import jakarta.persistence.*

@Entity
@Table(name = "region")
class Region ( //ver2
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,
        var nameKorean: String,
        var nameEnglish: String,

        @ManyToOne(fetch = FetchType.LAZY) //단방향 자가 참조
        @JoinColumn(name = "parent_id")
        var parent: Region?,
){

        constructor(createDto: CreateRegionDto, parentRegion: Region? ) : this(
                nameKorean = createDto.nameKorean,
                nameEnglish = createDto.nameEnglish,
                parent = parentRegion
        )

        fun isParentNull() : Boolean{
                return parent == null
        }

        fun updateNames(dto: UpdateRegionDto){
                this.nameKorean = dto.nameKorean
                this.nameEnglish = dto.nameEnglish
        }

        fun updateParent(newParentRegion : Region?){
                this.parent = newParentRegion
        }
}