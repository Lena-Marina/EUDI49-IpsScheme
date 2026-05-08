package at.asitplus.wallet.healthid

import at.asitplus.signum.indispensable.cosef.io.coseCompliantSerializer
import at.asitplus.wallet.ips.IPS
import at.asitplus.wallet.lib.data.vckJsonSerializer
import de.infix.testBalloon.framework.core.testSuite
import io.kotest.matchers.shouldBe
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray

@OptIn(ExperimentalSerializationApi::class)
val SdJwtSerializationTest by testSuite {

    test("serialize credential") {
        val credential = IPS(
            healthInsuranceId = randomString(),
            patientId = randomString(),
            taxNumber = randomString(),
            oneTimeToken = randomString(),
            ePrescriptionCode = randomString(),
            affiliationCountry = randomString(),
            issueDate = randomInstant(),
            expiryDate = randomInstant(),
            issuingAuthority = randomString(),
            documentNumber = randomString(),
            administrativeNumber = randomString(),
            issuingCountry = randomString(),
            issuingJurisdiction = randomString(),
        )
        val json = vckJsonSerializer.encodeToString(credential)
        vckJsonSerializer.decodeFromString<IPS>(json) shouldBe credential

        val cbor = coseCompliantSerializer.encodeToByteArray(credential)
        coseCompliantSerializer.decodeFromByteArray<IPS>(cbor) shouldBe credential
    }

}
