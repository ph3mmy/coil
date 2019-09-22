package coil.decode

import android.media.MediaDataSource
import android.os.Build.VERSION_CODES.M
import androidx.annotation.RequiresApi
import okio.BufferedSource

@RequiresApi(M)
internal class BufferedMediaDataSource(private val source: BufferedSource) : MediaDataSource() {

    override fun readAt(position: Long, buffer: ByteArray, offset: Int, size: Int): Int {
        if (!source.request(position + size)) return -1

        val peekSource = source.peek()
        peekSource.skip(position)
        return peekSource.read(buffer, offset, size)
    }

    override fun getSize() = -1L

    override fun close() = source.close()
}