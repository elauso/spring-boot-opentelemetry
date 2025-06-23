package net.elau.example.spring_boot_opentelemetry.repository

import net.elau.example.spring_boot_opentelemetry.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
}