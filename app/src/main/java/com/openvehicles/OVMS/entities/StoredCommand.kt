package com.openvehicles.OVMS.entities

import java.io.Serializable

class StoredCommand(

    @JvmField
    var key: Long,
    @JvmField
    var title: String,
    @JvmField
    var command: String

) : Comparable<StoredCommand>, Serializable {

    constructor(title: String, command: String) : this(0, title, command)

    override fun compareTo(other: StoredCommand): Int {
        return title.compareTo(other.title, ignoreCase = true)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is StoredCommand) {
            other.key == key
        } else {
            false
        }
    }
}
