package net.elau.example.spring_boot_opentelemetry.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogExecution(val message: String = "", val level: String = "INFO")

@Aspect
@Component
class LogExecutionAspect {

    private val logger = LoggerFactory.getLogger(LogExecutionAspect::class.java)

    @Around("@annotation(LogExecution)")
    fun logExecution(joinPoint: ProceedingJoinPoint): Any {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method

        val annotation = method.getAnnotation(LogExecution::class.java)
            ?: return joinPoint.proceed()

        val result = joinPoint.proceed()

        val methodName = joinPoint.signature.name
        val logMessage = annotation.message
            .replace("{method}", methodName)
            .replace("{result}", result.toString())

        val logFinal = "m=$methodName, msg=$logMessage"

        when (annotation.level.uppercase()) {
            "DEBUG" -> logger.debug(logFinal)
            "WARN" -> logger.warn(logFinal)
            "ERROR" -> logger.error(logFinal)
            else -> logger.info(logFinal)
        }

        return result
    }
}