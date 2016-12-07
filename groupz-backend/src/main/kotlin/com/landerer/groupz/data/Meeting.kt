package com.landerer.groupz.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by andreaslanderer on 07/12/2016.
 */
@Entity
data class Meeting(@Id @GeneratedValue(strategy=GenerationType.AUTO) var id: Long = 0L,
                   val name: String,
                   val admin: String) {
    constructor(): this(0L, "", "")
}