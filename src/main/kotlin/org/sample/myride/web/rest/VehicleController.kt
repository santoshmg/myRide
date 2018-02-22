package org.sample.myride.web.rest

import org.sample.myride.domain.Vehicle
import org.sample.myride.exception.EntityNotFoundException
import org.sample.myride.service.VehicleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@RestController
@RequestMapping("/api/vehicles")
class VehicleController(private val vehicleService: VehicleService) {

    @GetMapping("/{id}")
    @Throws(EntityNotFoundException::class)
    fun getVehicle(@PathVariable("id") id: Long): ResponseEntity<Vehicle>  {
        val vehicle = vehicleService.findVehicle(id)
        return ResponseEntity.ok(vehicle)
    }

    @PostMapping
    fun saveVehicle(@RequestBody vehicle: Vehicle): ResponseEntity<Vehicle> {
        vehicleService.save(vehicle)
        return ResponseEntity.created(location(vehicle)).build()
    }

    private fun location(vehicle: Vehicle): URI? {
        return UriComponentsBuilder
                .fromPath("/api/vehicles/{id}")
                .buildAndExpand(vehicle.id)
                .toUri()
    }

}

