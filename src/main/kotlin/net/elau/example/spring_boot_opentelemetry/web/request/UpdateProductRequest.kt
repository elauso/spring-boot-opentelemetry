package net.elau.example.spring_boot_opentelemetry.web.request

import net.elau.example.spring_boot_opentelemetry.model.CategoryType
import java.math.BigDecimal

data class UpdateProductRequest(
    val name: String?,

    val description: String?,

    val price: BigDecimal?,

    val category: CategoryType?
)
