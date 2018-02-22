package org.sample.myride.service

import org.sample.myride.domain.Vehicle
import org.springframework.stereotype.Service

@Service
class VehicleService {
    fun findVehicle(vehicleId: Long): Vehicle {
        return Vehicle(12345L, "BMW", 4)
    }

    fun save(vehicle: Vehicle): Vehicle {
        return Vehicle(12345L, "BMW", 4)
    }

}