package com.landerer.groupz.repository

import com.landerer.groupz.data.Meeting
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by andreaslanderer on 07/12/2016.
 */
interface MeetingRepository : JpaRepository<Meeting, Long> {
    fun findByName(name: String): Meeting?
}