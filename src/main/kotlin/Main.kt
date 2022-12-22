import org.openrs2.cache.Cache
import java.nio.file.Path

fun main(args : Array<String>) {
    println("Hello, World!")

    val path = System.getProperty("user.dir")

    println("Working Directory = $path")
    val cache = Cache.open(Path.of("./cache/"))
}