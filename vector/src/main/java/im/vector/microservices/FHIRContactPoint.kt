package im.vector.microservices

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FHIRContactPoint (
        @JvmField
        var name: String,
        @JvmField
        var value: String,
        @JvmField
        var rank: Int,
        @JvmField
        var type: String,
        @JvmField
        var use: String?
) : Parcelable