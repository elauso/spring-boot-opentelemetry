package net.elau.example.spring_boot_opentelemetry.dto

import net.elau.example.spring_boot_opentelemetry.model.CategoryType
import java.math.BigDecimal
import java.util.*

data class UpdateProductDTO(
    val id: Long,

    val name: String?,

    val description: String?,

    val price: BigDecimal?,

    val category: CategoryType?,

    val userId: UUID
)
