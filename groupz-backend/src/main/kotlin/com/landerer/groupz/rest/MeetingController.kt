package com.landerer.groupz.rest

import com.landerer.groupz.data.Meeting
import com.landerer.groupz.repository.MeetingRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder



/**
 * Created by andreaslanderer on 07/12/2016.
 */
@RestController
@RequestMapping("/meetings")
class MeetingController(private val repository: MeetingRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestBody request: CreateMeetingRequest): ResponseEntity<GeneralMeetingResponse> {
        val meeting = Meeting(name = request.name, admin = request.admin)
        repository.saveAndFlush(meeting)
        val location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(meeting.id).toUri()
        if (location != null) {
            return ResponseEntity.created(location)
                    .body(GeneralMeetingResponse(meeting.id, meeting.name, meeting.admin))
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(GeneralMeetingResponse())
        }
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun read(): ResponseEntity<List<GeneralMeetingResponse>> {
        val meetings = repository.findAll()
        if(meetings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listOf())
        } else {
            return ResponseEntity.ok(meetings.map { meeting ->
                GeneralMeetingResponse(meeting.id, meeting.name, meeting.admin) })
        }
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun read(@PathVariable id: Long): ResponseEntity<GeneralMeetingResponse> {
        val meeting = repository.findOne(id)
        if (meeting != null) {
            return ResponseEntity.ok(GeneralMeetingResponse(meeting.id, meeting.name, meeting.admin))
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GeneralMeetingResponse())
    }
}

data class CreateMeetingRequest(var name: String, var admin: String) {
    constructor(): this("", "")
}

data class GeneralMeetingResponse(var id: Long, var name: String, var admin: String) {
    constructor(): this(0L, "", "")
}