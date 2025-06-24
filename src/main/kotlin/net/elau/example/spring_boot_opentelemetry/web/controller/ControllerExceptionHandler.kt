package net.elau.example.spring_boot_opentelemetry.web.controller

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Any> {
        val span = Span.current()

        if (span.spanContext.isValid) {
            span.recordException(ex)
            span.setStatus(StatusCode.ERROR, ex.message ?: "Unknown error")
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf("error" to (ex.message ?: "Unexpected error")))
    }
}