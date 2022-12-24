import org.openrs2.cache.Cache
import java.nio.file.Path

fun main(args : Array<String>) {
    val path = System.getProperty("user.dir")
    println("Working Directory = $path")

    val cache = Cache.open(Path.of("./cache/"))
    val data = cache.read(2,10,1042)

    println("Data len: " + data.capacity())

    //cache.write()
}