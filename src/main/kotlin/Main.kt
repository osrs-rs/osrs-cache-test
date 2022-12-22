import org.openrs2.cache.Cache
import java.nio.file.Path

fun main(args : Array<String>) {
    println("Hello, World!")
    val cache = Cache.open(Path.of("./some-cache/"))
}