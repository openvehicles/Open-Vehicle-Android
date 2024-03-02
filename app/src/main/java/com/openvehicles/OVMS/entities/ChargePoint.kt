package com.openvehicles.OVMS.entities

import com.google.gson.annotations.SerializedName

/**
 * OCM charge point
 *
 */
class ChargePoint {

    @JvmField
    @SerializedName("ID")
    var id: String? = null

    @JvmField
    @SerializedName("OperatorInfo")
    var operatorInfo: OperatorInfo

    @JvmField
    @SerializedName("UsageType")
    var usageType: UsageType
    @JvmField
    @SerializedName("UsageCost")
    var usageCost: String? = null

    @JvmField
    @SerializedName("AddressInfo")
    var addressInfo: AddressInfo
    @JvmField
    @SerializedName("NumberOfPoints")
    var numberOfPoints: String? = null
    @JvmField
    @SerializedName("GeneralComments")
    var generalComments: String? = null

    @JvmField
    @SerializedName("StatusType")
    var statusType: StatusType
    @JvmField
    @SerializedName("DateLastStatusUpdate")
    var dateLastStatusUpdate: String? = null

    @JvmField
    @SerializedName("Connections")
    var connections: Array<Connection?>

    init {
        // create sub class members:
        operatorInfo = OperatorInfo()
        usageType = UsageType()
        addressInfo = AddressInfo()
        statusType = StatusType()
        connections = arrayOfNulls(0)
    }

    /*
     * Inner types
     */

    class OperatorInfo {
        @JvmField
        @SerializedName("Title")
        var title: String? = null
    }

    class UsageType {
        @JvmField
        @SerializedName("Title")
        var title: String? = null
    }

    class AddressInfo {
        @JvmField
        @SerializedName("Title")
        var title: String? = null
        @JvmField
        @SerializedName("AddressLine1")
        var addressLine1: String? = null
        @JvmField
        @SerializedName("Latitude")
        var latitude: String? = null
        @JvmField
        @SerializedName("Longitude")
        var longitude: String? = null
        @JvmField
        @SerializedName("AccessComments")
        var accessComments: String? = null
        @JvmField
        @SerializedName("RelatedURL")
        var relatedUrl: String? = null
    }

    class StatusType {
        @JvmField
        @SerializedName("Title")
        var title: String? = null
    }

    class Connection {

        @JvmField
        @SerializedName("ConnectionType")
        var connectionType: ConnectionType? = null
        @JvmField
        @SerializedName("Level")
        var level: Level? = null

        class ConnectionType {
            @JvmField
            @SerializedName("ID")
            var id: String? = null
            @JvmField
            @SerializedName("Title")
            var title: String? = null
        }

        class Level {
            @JvmField
            @SerializedName("Title")
            var title: String? = null
        }
    }
}
