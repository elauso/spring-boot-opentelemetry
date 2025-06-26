package net.elau.example.spring_boot_opentelemetry.web.controller

import net.elau.example.spring_boot_opentelemetry.aspect.LogExecution
import net.elau.example.spring_boot_opentelemetry.aspect.MetricFromReturnValue
import net.elau.example.spring_boot_opentelemetry.mapper.toDTO
import net.elau.example.spring_boot_opentelemetry.mapper.toResponse
import net.elau.example.spring_boot_opentelemetry.service.ProductService
import net.elau.example.spring_boot_opentelemetry.web.request.CreateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.request.UpdateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.response.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(private val service: ProductService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LogExecution("Product created with success productId={result.id}")
    @MetricFromReturnValue(name = "product_create_requests_total", tags = ["category:return.category.name"])
    fun create(
        @RequestHeader("X-User-Id") userId: UUID,
        @RequestBody createProductRequest: CreateProductRequest
    ): ProductResponse =
        service.create(createProductRequest.toDTO(userId)).toResponse()

    @PatchMapping("/{id}")
    @LogExecution("Product updated with success productId={result.id}")
    @MetricFromReturnValue(name = "product_update_requests_total", tags = ["category:return.category.name"])
    fun update(
        @RequestHeader("X-User-Id") userId: UUID,
        @PathVariable id: Long,
        @RequestBody updateProductRequest: UpdateProductRequest
    ): ProductResponse =
        service.update(updateProductRequest.toDTO(id, userId)).toResponse()
}
