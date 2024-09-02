package org.aditilearn.phone.datasource.impl

import org.aditilearn.phone.datasource.PhoneDataSource
import org.aditilearn.phone.model.Phone
import org.springframework.stereotype.Repository

@Repository
class PhoneDataSourceImpl : PhoneDataSource {
    val phones = mutableListOf(

    Phone(1,"Apple", 99.9, 128),
    Phone(2,"Samsung", 79.9, 256),
    Phone(3, "Google", 59.9, 128),
    Phone(4, "OnePlus", 72.9, 256),
    Phone(5,"Sony",99.9, 128))

    override fun retrievePhones(): Collection<Phone> {
       return phones
   }

   override fun retrievePhone(brand : String): Phone{
       return phones.first{it.brand == brand}
   }
    override fun createPhone(phone : Phone): Phone {
        if(phones.any { it.id == phone.id }) {
            throw IllegalArgumentException("bank with account number ${phone.brand} already exits")
        }
        phones.add(phone)
        return phone
    }

    override fun updatePhone(phone: Phone): Phone {
        val updatedPhone = phones.firstOrNull{it.id == phone.id}
        ?: throw NoSuchElementException("could not find a phone with this brand name ")
        phones.remove(updatedPhone)
        phones.add(phone)
        return phone
    }

    override fun updatePhones(phones : List<Phone>)  {
        println("In the method of updating phones")
        // Check if all phones in the input list are present in the existing list
        println("New Phones: $phones")
        println("Old phones: ${this.phones}")
        val allPhonesPresent = phones.all { newPhone : Phone->
            this.phones.any { oldPhone -> oldPhone.id == newPhone.id }
        }

        // Throw an exception if any phone in the input list is not found in the existing list
        if (!allPhonesPresent) {
            throw NoSuchElementException("All phones are not available for update")
        }

        // Replace all existing phones with the ones in the input list
        this.phones.replaceAll { oldPhone ->
            phones.find { newPhone -> newPhone.id == oldPhone.id } ?: oldPhone
        }
    }
    override fun deletePhone(id: Int) {
        val currentPhone = phones.firstOrNull { it.id == id }
                ?:throw NoSuchElementException(" could not find a bank with account number ")
        phones.remove(currentPhone)

//        this.phones.removeIf { it.id == id }
    }
}