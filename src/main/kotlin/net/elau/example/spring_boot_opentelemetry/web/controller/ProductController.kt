package net.elau.example.spring_boot_opentelemetry.web.controller

import io.micrometer.core.instrument.MeterRegistry
import net.elau.example.spring_boot_opentelemetry.mapper.toDTO
import net.elau.example.spring_boot_opentelemetry.mapper.toResponse
import net.elau.example.spring_boot_opentelemetry.service.ProductService
import net.elau.example.spring_boot_opentelemetry.web.request.CreateProductRequest
import net.elau.example.spring_boot_opentelemetry.web.response.ProductResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val meterRegistry: MeterRegistry, private val service: ProductService) {
    companion object {
        private val log = LoggerFactory.getLogger(ProductController::class.java)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody createProductRequest: CreateProductRequest): ProductResponse =
        createProductRequest
            .let { request ->
                service.create(request.toDTO())
            }.also { result ->
                log.info("m=create, msg=Product created with success productId={}", result.id)
                meterRegistry.counter("product_create_requests_total", "category", result.category.name).increment()
            }.toResponse()
}
