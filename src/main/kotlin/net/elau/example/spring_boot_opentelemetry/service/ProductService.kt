package net.elau.example.spring_boot_opentelemetry.service

import io.opentelemetry.api.trace.Span
import io.opentelemetry.instrumentation.annotations.WithSpan
import net.elau.example.spring_boot_opentelemetry.dto.CreateProductDTO
import net.elau.example.spring_boot_opentelemetry.dto.ProductDTO
import net.elau.example.spring_boot_opentelemetry.dto.UpdateProductDTO
import net.elau.example.spring_boot_opentelemetry.mapper.merge
import net.elau.example.spring_boot_opentelemetry.mapper.toDTO
import net.elau.example.spring_boot_opentelemetry.mapper.toModel
import net.elau.example.spring_boot_opentelemetry.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val repository: ProductRepository) {

    @Transactional
    @WithSpan("product-service.create")
    fun create(createProductDTO: CreateProductDTO): ProductDTO =
        createProductDTO
            .apply {
                Span.current().setAttribute("user.id", this.userId.toString())
            }.let { createProduct ->
                repository.save(createProduct.toModel())
            }.toDTO()

    @Transactional
    @WithSpan("product-service.update")
    fun update(updateProductDTO: UpdateProductDTO): ProductDTO =
        updateProductDTO
            .apply {
                Span.current().setAttribute("user.id", this.userId.toString())
            }.let { updateProduct ->
                val product = repository.findById(updateProduct.id)
                    .orElseThrow { IllegalArgumentException("Product with id ${updateProduct.id} not found") }
                product.apply { merge(updateProduct) }.toDTO()
            }
}
