import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap
import org.openrs2.cache.Cache
import org.openrs2.cache.DiskStore
import org.openrs2.cache.Js5Compression
import org.openrs2.cache.Js5CompressionType
import org.openrs2.crypto.XteaKey
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

fun ByteArray.toHex(): String = joinToString(separator = ", 0x") { eachByte -> "%02x".format(eachByte) }

fun main(args : Array<String>) {
    testCacheRead()
    testCacheReadEncrypted()
    testCacheReadNamedGroup()
    testCacheReadNamedGroupEncrypted()

    bluePartyhatTest()

    //val path = System.getProperty("user.dir")
    //println("Working Directory = $path")
    val startTime = System.currentTimeMillis()

    val cache = Cache.open(Path.of("cachetest/cache-read"))
    // Party hat
    //val data = cache.read(2,10,1042)
    //val data = cache.read(10,"huffman",0)
    val data = cache.read(0,0,0)

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

private fun testCacheRead() {
    val path = Path.of("cachetest", "cache-read")
    path.createDirectories()
    val store = DiskStore.create(path)
    store.create(0)
    store.create(255)
    val cache = Cache.open(store)
    cache.write(0, 0, 0, Unpooled.wrappedBuffer("OpenRS2".toByteArray()))
    cache.close()
}

private val KEY = XteaKey.fromHex("00112233445566778899AABBCCDDEEFF")
private fun testCacheReadEncrypted() {
    val path = Path.of("cachetest", "cache-read-encrypted")
    path.createDirectories()
    val store = DiskStore.create(path)
    store.create(0)
    store.create(255)
    val cache = Cache.open(store)
    cache.write(0, 0, 0, Unpooled.wrappedBuffer("OpenRS2".toByteArray()), KEY)
    cache.close()
}

private fun testCacheReadNamedGroup() {
    val path = Path.of("cachetest", "cache-read-named-group")
    path.createDirectories()
    val store = DiskStore.create(path)
    store.create(0)
    store.create(255)
    val cache = Cache.open(store)
    cache.write(0, "OpenRS2", 0, Unpooled.wrappedBuffer("OpenRS2".toByteArray()))
    cache.close()
}

private fun testCacheReadNamedGroupEncrypted() {
    val path = Path.of("cachetest", "cache-read-named-group-encrypted")
    path.createDirectories()
    val store = DiskStore.create(path)
    store.create(0)
    store.create(255)
    val cache = Cache.open(store)
    cache.write(0, "OpenRS2", 0, Unpooled.wrappedBuffer("OpenRS2".toByteArray()),KEY)
    cache.close()
}

private fun bluePartyhatTest() {
    val cache = Cache.open(Path.of("cache"))
    // Party hat
    val data = cache.read(2,10,1042)

    val bytes = ByteArray(data.readableBytes())
    data.readBytes(bytes)

    println("Data len: " + data.capacity())
    println("Data bytes: 0x" + bytes.toHex())
}

/*private fun makeNewCacheBzip2() {
    // Create cache and write file
    val path = Path.of("cachetest", "cache-read-bzip2")
    path.createDirectories()
    val store = DiskStore.create(path)
    store.create(0)
    store.create(255)
    val cache = Cache.open(store)
    cache.write(0, 0, 0, Unpooled.wrappedBuffer("OpenRS2".toByteArray()))
    cache.close()

    // Compress with bzip2
    val store2 = DiskStore.open(path)
    store2.write(0,0, Js5Compression.compress(store2.read(0,0), Js5CompressionType.BZIP2))
    store2.close()
}*/