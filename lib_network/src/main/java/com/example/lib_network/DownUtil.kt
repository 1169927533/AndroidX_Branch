import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.lib_network.util.AppCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream


/**
 * Created by jingzz on 2020/7/9.
 */
typealias DOWLOAD_ERROR = (Throwable) -> Unit
typealias DOWLOAD_PROCESS = (downloadedSize: Long, length: Long, process: Float) -> Unit
typealias DOWLOAD_SUCCESS = (uri: Uri) -> Unit

suspend fun dowload(response: Response<ResponseBody>, block: DownUtil.() -> Unit): DownUtil {
    val build = DownUtil(response)
    build.block()
    return build
}

class DownUtil(val response: Response<ResponseBody>) {
    private var error: DOWLOAD_ERROR = {} //错误贺词
    private var process: DOWLOAD_PROCESS = { downloadedSize, filsLength, process -> } //进度
    private var success: DOWLOAD_SUCCESS = {} //下载完成
    private val context: Context = AppCache.getContext() //全局context
    var setUri: () -> Uri? = { null } //设置下载的uri
    var setFileName: () -> String? = { null } //设置文件名

    fun process(process: DOWLOAD_PROCESS) {
        this.process = process
    }

    fun error(error: DOWLOAD_ERROR) {
        this.error = error
    }

    fun success(success: DOWLOAD_SUCCESS) {
        this.success = success
    }

    suspend fun startDowload() {
        withContext(Dispatchers.Main) {
            //使用流获取下载进度
            flow.flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is DowloadStatus.DowloadErron -> error(it.t)
                        is DowloadStatus.DowloadProcess -> process(
                            it.currentLength,
                            it.length,
                            it.process
                        )
                        is DowloadStatus.DowloadSuccess -> success(it.uri)
                    }
                }
        }
    }

    val flow = flow<DowloadStatus> {
        try {
            val body = response.body() ?: throw RuntimeException("下载出错")
            //文件总长度
            val length = body.contentLength()
            //文件minetype
            val contentType = body.contentType()
            val ios = body.byteStream()
            // var s = BitmapFactory.decodeStream(ios)

            var uri: Uri? = null
            var file: File? = null
            val ops = kotlin.run {
                setUri()?.let {
                    //url转OutPutStream
                    uri = it
                    context.contentResolver.openOutputStream(it)
                } ?: kotlin.run {
                    val fileName = setFileName() ?: kotlin.run {
                        //如果连文件名都不给，那就自己生成文件名
                        "${System.currentTimeMillis()}.${contentType?.subtype()}"
                    }
                    file =
                        File("${context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)}${File.separator}$fileName")
                    FileOutputStream(file)
                }
            }
            //下载的长度
            var currentLength: Int = 0
            //写入文件
            val bufferSize = 1024 * 8
            val buffer = ByteArray(bufferSize)
            val bufferedInputStream = BufferedInputStream(ios, bufferSize)
            var readLength: Int = 0
            while (bufferedInputStream.read(buffer, 0, bufferSize)
                    .also { readLength = it } != -1
            ) {
                ops.write(buffer, 0, readLength)
                currentLength += readLength
                emit(
                    DowloadStatus.DowloadProcess(
                        currentLength.toLong(),
                        length,
                        currentLength.toFloat() / length.toFloat()
                    )
                )
            }
            bufferedInputStream.close()
            ops.close()
            ios.close()
            if (uri != null) {
                emit(DowloadStatus.DowloadSuccess(uri!!))
            } else if (file != null) {
                emit(DowloadStatus.DowloadSuccess(Uri.fromFile(file)))
            }

        } catch (e: Exception) {
            emit(DowloadStatus.DowloadErron(e))
        }
    }




}

sealed class DowloadStatus {
    class DowloadProcess(val currentLength: Long, val length: Long, val process: Float) :
        DowloadStatus()

    class DowloadErron(val t: Throwable) : DowloadStatus()
    class DowloadSuccess(val uri: Uri) : DowloadStatus()
}
 