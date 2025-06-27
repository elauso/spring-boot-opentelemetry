package net.elau.example.spring_boot_opentelemetry.aspect

import io.micrometer.core.instrument.MeterRegistry
import net.elau.example.spring_boot_opentelemetry.util.resolveTags
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MetricFromArgument(
    val name: String,
    val tags: Array<String> = []
)

@Aspect
@Component
class MetricFromArgumentAspect(private val meterRegistry: MeterRegistry) {

    @Around("@annotation(MetricFromArgument)")
    fun intercept(joinPoint: ProceedingJoinPoint): Any {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method

        val annotation = method.getAnnotation(MetricFromArgument::class.java)
            ?: return joinPoint.proceed()

        val result = joinPoint.proceed()

        val tags = resolveTags(annotation.tags, joinPoint.args, null)
        meterRegistry.counter(annotation.name, *tags).increment()

        return result
    }
}