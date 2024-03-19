package io.directional.wine.domain.regionv2


import io.directional.wine.domain.region.dto.CreateRegionDto
import io.directional.wine.domain.region.dto.UpdateRegionDto
import jakarta.persistence.*

@Entity
@Table(name = "region_v2")
class RegionV2 ( //ver2
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long? = null,
        var nameKorean: String,
        var nameEnglish: String,

        @ManyToOne(fetch = FetchType.LAZY) //단방향 자가 참조
        @JoinColumn(name = "parent_id")
        var parent: RegionV2?,
){

        constructor(createDto: CreateRegionDto, parentRegionV2: RegionV2? ) : this(
                nameKorean = createDto.nameKorean,
                nameEnglish = createDto.nameEnglish,
                parent = parentRegionV2
        )

        fun isParentNull() : Boolean{
                return parent == null
        }

        fun updateNames(dto: UpdateRegionDto){
                this.nameKorean = dto.nameKorean
                this.nameEnglish = dto.nameEnglish
        }

        fun updateParent(newParentRegionV2 : RegionV2?){
                this.parent = newParentRegionV2
        }
}