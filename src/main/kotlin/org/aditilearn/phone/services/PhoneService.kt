package org.aditilearn.phone.services

import org.aditilearn.phone.datasource.PhoneDataSource
import org.aditilearn.phone.model.Phone
import org.springframework.stereotype.Service

@Service
class PhoneService(private val dataSource : PhoneDataSource ) {
    fun getPhones(): Collection<Phone> {
    return this.dataSource.retrievePhones()
    }

    fun getPhone(brand: String): Phone {
    return dataSource.retrievePhone(brand)

    }

    fun addPhone(phone: Phone): Phone {
    return dataSource.createPhone(phone)
    }

    fun updatePhone(phone: Phone): Phone =
     dataSource.updatePhone(phone)

    fun updatePhones(phones: List<Phone>) {
        dataSource.updatePhones(phones)
    }

    fun deletePhone(id: Int) {
        dataSource.deletePhone(id)
    }

}