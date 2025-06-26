package net.elau.example.spring_boot_opentelemetry.mapper

import net.elau.example.spring_boot_opentelemetry.dto.CreateProductDTO
import net.elau.example.spring_boot_opentelemetry.dto.ProductDTO
import net.elau.example.spring_boot_opentelemetry.dto.UpdateProductDTO
import net.elau.example.spring_boot_opentelemetry.exception.NullParameterException
import net.elau.example.spring_boot_opentelemetry.model.Product
import net.elau.example.spring_boot_opentelemetry.web.request.CreateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.request.UpdateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.response.ProductResponse
import java.util.*

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

fun CreateProductRequest.toDTO(userId: UUID) = CreateProductDTO(
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

fun Product.merge(updateProductDTO: UpdateProductDTO) {
    updateProductDTO.name?.let { name = it }
    updateProductDTO.description?.let { description = it }
    updateProductDTO.price?.let { price = it }
    updateProductDTO.category?.let { category = it }
}

fun UpdateProductRequest.toDTO(id: Long, userId: UUID) = UpdateProductDTO(
    id = id,
    name = name,
    description = description,
    price = price,
    category = category,
    userId = userId
)
