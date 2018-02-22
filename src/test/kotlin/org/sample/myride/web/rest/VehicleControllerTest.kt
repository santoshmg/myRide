package org.sample.myride.web.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.sample.myride.domain.Vehicle
import org.sample.myride.exception.EntityNotFoundException
import org.sample.myride.service.VehicleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders.*
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    companion object {
        private val API_PATH = "/api/vehicles"
        private val VEHICLE_ID = 12345L
        private val BRAND = "BMW"
        private val SEAT_COUNT = 4

    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vehicleService: VehicleService

    @Test
    fun `getVehicle - success`() {

        given(vehicleService.findVehicle(VEHICLE_ID))
                .willReturn(vehicle())

        mockMvc.perform(get("$API_PATH/{id}", VEHICLE_ID))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(VEHICLE_ID))
                .andExpect(jsonPath("$.brand").value(BRAND))
                .andExpect(jsonPath("$.seatCount").value(SEAT_COUNT))
                .andDo(MockMvcResultHandlers.print())

        verify(vehicleService).findVehicle(VEHICLE_ID)
    }

    @Test
    fun `getVehicle - returns not found if not exist`() {

        val errorDescription = "Vehicle not found"
        given(vehicleService.findVehicle(VEHICLE_ID))
                .willThrow(EntityNotFoundException("NOT_FOUND",errorDescription ))

        mockMvc.perform(get("$API_PATH/{id}", VEHICLE_ID))
                .andExpect(status().isNotFound)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.error_code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.error_description").value(errorDescription))

        verify(vehicleService).findVehicle(VEHICLE_ID)
    }

    @Test
    fun `saveVehicle - success`() {
        val mapper = ObjectMapper()

        given(vehicleService.save(vehicle()))
                .willReturn(vehicle())

        mockMvc.perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(vehicle())))
                .andExpect(status().isCreated)
                .andExpect(header().string(LOCATION,"$API_PATH/$VEHICLE_ID"))

        verify(vehicleService).save(vehicle())
    }

    private fun vehicle() = Vehicle(VEHICLE_ID, BRAND, SEAT_COUNT)
}

