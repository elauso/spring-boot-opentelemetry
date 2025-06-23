package net.elau.example.spring_boot_opentelemetry.dto

import net.elau.example.spring_boot_opentelemetry.model.CategoryType
import java.math.BigDecimal

data class CreateProductDTO(
    val name: String,

    val description: String? = null,

    val price: BigDecimal,

    val category: CategoryType
)
