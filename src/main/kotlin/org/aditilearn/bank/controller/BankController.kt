package org.aditilearn.bank.controller
import org.aditilearn.bank.model.Bank
import org.aditilearn.bank.services.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank {
        println("Need bank details for account: $accountNumber")
        return service.getBank(accountNumber)
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBanks(@RequestBody bank: Bank): Bank = service.addBank(bank)

    @PatchMapping
    fun updateBank(@RequestBody bank: Bank): Bank = service.updateBank(bank)

   @DeleteMapping("/{accountNumber}")
   @ResponseStatus(HttpStatus .NO_CONTENT)
   fun deleteBank(@PathVariable  accountNumber: String) {
       service.deleteBank(accountNumber)
      }}