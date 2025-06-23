package net.elau.example.spring_boot_opentelemetry.dto

import net.elau.example.spring_boot_opentelemetry.model.CategoryType
import java.math.BigDecimal

data class ProductDTO(
    val id: Long,

    val name: String,

    val description: String?,

    val price: BigDecimal,

    val category: CategoryType
)
