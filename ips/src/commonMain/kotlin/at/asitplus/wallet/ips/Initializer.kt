package at.asitplus.wallet.ips

import at.asitplus.wallet.lib.JsonValueEncoder
import at.asitplus.wallet.lib.LibraryInitializer
import at.asitplus.wallet.lib.data.vckJsonSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.time.Instant
import kotlinx.serialization.json.encodeToJsonElement

object Initializer {

    /**
     * A reference to this class is enough to trigger the init block
     */
    init {
        initWithVCK()
    }

    /**
     * This has to be called first, before anything first, to load the
     * relevant classes of this library into the base implementations of VC-K
     */
    fun initWithVCK() {
        LibraryInitializer.registerExtensionLibrary(
            credentialScheme = IpsScheme,
            jsonValueEncoder = jsonValueEncoder(),
            itemValueSerializerMap = mapOf(
                IpsScheme.Attributes.ISSUE_DATE to Instant.serializer(),
                IpsScheme.Attributes.EXPIRY_DATE to Instant.serializer(),
            )
        )
    }

    private fun jsonValueEncoder(): JsonValueEncoder = {
        when (it) {
            is Instant -> vckJsonSerializer.encodeToJsonElement(it)
            else -> null
        }
    }


}
