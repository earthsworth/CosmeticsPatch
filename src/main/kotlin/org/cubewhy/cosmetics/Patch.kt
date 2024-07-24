package org.cubewhy.cosmetics

import io.github.nilsen84.lcqt.patches.CosmeticsPatch
import org.cubewhy.patch.api.PatchEntry
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import kotlin.system.exitProcess

@Suppress("unused")
internal class Patch : PatchEntry {
    private val patch = CosmeticsPatch()

    private fun transform(classFileBuffer: ByteArray): ByteArray {
        runCatching {
            val cn = ClassNode()
            val cr = ClassReader(classFileBuffer)
            cr.accept(cn, 0)

            patch.transform(cn)

            val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
            cn.accept(cw)
            return cw.toByteArray()
        }.getOrElse {
            it.printStackTrace()
            exitProcess(1)
        }
    }

    override fun patchClass(name: String, klass: ByteArray): ByteArray {
        return transform(klass)
    }

    override fun patchFile(name: String, bytes: ByteArray): ByteArray {
        return bytes
    }
}