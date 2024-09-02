package org.aditilearn.phone.controller

import org.aditilearn.phone.model.Phone
import org.aditilearn.phone.services.PhoneService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/Mobile")

class PhoneController(private val service: PhoneService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun elementNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getPhones(): Collection<Phone> =
            service.getPhones()

    @GetMapping("/{brand}")
    fun getPhone(@PathVariable brand: String): Phone {
        return service.getPhone(brand)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPhone(@RequestBody phone: Phone): Phone =
            service.addPhone(phone)

    @PatchMapping("single")
    fun updatePhone(@RequestBody phone: Phone): Phone =
            service.updatePhone(phone)

    @PatchMapping("multiple")
    fun updatePhones(@RequestBody phones: List<Phone>) {
        service.updatePhones(phones)
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePhone(@PathVariable id :Int ) =
        service.deletePhone(id)

//    @DeleteMapping
//    fun deletePhonesByBody(@RequestBody phone: Phone) =
//        service.deletePhone(phone.id)

}
