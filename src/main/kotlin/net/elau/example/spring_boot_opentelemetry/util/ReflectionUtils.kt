package net.elau.example.spring_boot_opentelemetry.util

fun resolveTags(tagExpressions: Array<String>, args: Array<Any>, result: Any?): Array<String> {
    return tagExpressions.mapNotNull { tag ->
        val (key, valueExpr) = tag.split(":")
        val value = when {
            valueExpr.startsWith("arg[") -> extractFromArgs(valueExpr, args)
            valueExpr.startsWith("return.") -> extractFromField(result, valueExpr.removePrefix("return."))
            valueExpr == "return" -> result?.toString()
            else -> valueExpr
        }
        value?.let { key to it }
    }.flatMap { listOf(it.first, it.second) }.toTypedArray()
}

private fun extractFromArgs(expr: String, args: Array<Any>): String? {
    val regex = Regex("""arg\[(\d+)](?:\.(\w+))?""")
    val match = regex.matchEntire(expr) ?: return null
    val index = match.groupValues[1].toIntOrNull() ?: return null
    val field = match.groupValues.getOrNull(2)

    val arg = args.getOrNull(index) ?: return null
    return if (field != null) extractFromField(arg, field) else arg.toString()
}

private fun extractFromField(obj: Any?, field: String): String? {
    if (obj == null) return null
    return try {
        val f = obj::class.java.getDeclaredField(field)
        f.isAccessible = true
        f.get(obj)?.toString()
    } catch (ex: Exception) {
        null
    }
}