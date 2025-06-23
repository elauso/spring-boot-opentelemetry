package net.elau.example.spring_boot_opentelemetry.web.response

import net.elau.example.spring_boot_opentelemetry.model.CategoryType
import java.math.BigDecimal

data class ProductResponse(
    val id: Long,

    val name: String,

    val description: String?,

    val price: BigDecimal,

    val category: CategoryType
)
