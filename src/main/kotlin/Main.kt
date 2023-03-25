import org.openrs2.cache.Cache
import java.nio.file.Path

fun ByteArray.toHex(): String = joinToString(separator = ", 0x") { eachByte -> "%02x".format(eachByte) }

fun main(args : Array<String>) {
    //val path = System.getProperty("user.dir")
    //println("Working Directory = $path")
    val startTime = System.currentTimeMillis()

    val cache = Cache.open(Path.of("./cache2/"))
    // Party hat
    val data = cache.read(254,0,0)

    val endTime = System.currentTimeMillis()

    val elapsedTime = endTime - startTime
    println("Elapsed time: $elapsedTime ms")

    // Loading map data (GZIP compressed)
    //val data = cache.read(5,"m50_50",0)

    // Loading location data (XTEA encrypted + GZIP compressed)
    //val data = cache.read(5,"l50_50",0)

    val bytes = ByteArray(data.readableBytes())
    data.readBytes(bytes)

    println("Data len: " + data.capacity())
    println("Data bytes: 0x" + bytes.toHex())

    //cache.write()
}