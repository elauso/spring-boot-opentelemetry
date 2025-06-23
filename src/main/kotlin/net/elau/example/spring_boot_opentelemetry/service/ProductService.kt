package net.elau.example.spring_boot_opentelemetry.service

import net.elau.example.spring_boot_opentelemetry.dto.CreateProductDTO
import net.elau.example.spring_boot_opentelemetry.mapper.toDTO
import net.elau.example.spring_boot_opentelemetry.mapper.toModel
import net.elau.example.spring_boot_opentelemetry.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(private val repository: ProductRepository) {

    @Transactional
    fun create(createProductDTO: CreateProductDTO) =
        repository.save(createProductDTO.toModel()).toDTO()
}