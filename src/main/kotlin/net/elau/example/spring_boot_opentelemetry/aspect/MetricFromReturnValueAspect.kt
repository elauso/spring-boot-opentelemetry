package net.elau.example.spring_boot_opentelemetry.aspect

import io.micrometer.core.instrument.MeterRegistry
import net.elau.example.spring_boot_opentelemetry.util.resolveTags
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MetricFromReturnValue(
    val name: String,
    val tags: Array<String> = []
)

@Aspect
@Component
class MetricFromReturnValueAspect(private val meterRegistry: MeterRegistry) {

    @Around("@annotation(metricFromReturnValue)")
    fun intercept(joinPoint: ProceedingJoinPoint, annotation: MetricFromReturnValue): Any {
        val result = joinPoint.proceed()

        val tags = resolveTags(annotation.tags, joinPoint.args, result)
        meterRegistry.counter(annotation.name, *tags).increment()

        return result
    }
}