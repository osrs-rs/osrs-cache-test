import org.openrs2.cache.Cache
import java.nio.file.Path

fun ByteArray.toHex(): String = joinToString(separator = ", 0x") { eachByte -> "%02x".format(eachByte) }
fun main(args : Array<String>) {
    val path = System.getProperty("user.dir")
    println("Working Directory = $path")

    val cache = Cache.open(Path.of("./cache/"))
    val data = cache.read(2,10,1042)

    val bytes = ByteArray(data.readableBytes())
    data.readBytes(bytes)

    println("Data len: " + data.capacity())
    println("Data bytes: 0x" + bytes.toHex())

    //cache.write()
}