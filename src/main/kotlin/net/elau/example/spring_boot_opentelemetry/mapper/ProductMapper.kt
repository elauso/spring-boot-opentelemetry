package net.elau.example.spring_boot_opentelemetry.mapper

import net.elau.example.spring_boot_opentelemetry.dto.CreateProductDTO
import net.elau.example.spring_boot_opentelemetry.dto.ProductDTO
import net.elau.example.spring_boot_opentelemetry.exception.NullParameterException
import net.elau.example.spring_boot_opentelemetry.model.Product
import net.elau.example.spring_boot_opentelemetry.web.request.CreateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.response.ProductResponse

fun CreateProductDTO.toModel() = Product(
    name = name,
    description = description,
    price = price,
    category = category
)

fun Product.toDTO() = ProductDTO(
    id = id
        ?: throw NullParameterException("The id must not be null, the Product entity must be persisted before convert to ProductDTO"),
    name = name,
    description = description,
    price = price,
    category = category
)

fun CreateProductRequest.toDTO() = CreateProductDTO(
    name = name,
    description = description,
    price = price,
    category = category,
    userId = userId
)

fun ProductDTO.toResponse() = ProductResponse(
    id = id,
    name = name,
    description = description,
    price = price,
    category = category
)