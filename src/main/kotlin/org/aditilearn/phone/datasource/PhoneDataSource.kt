package org.aditilearn.phone.datasource

import org.aditilearn.phone.model.Phone

interface PhoneDataSource {
    fun retrievePhones(): Collection<Phone>
    fun retrievePhone(brand : String): Phone
    fun createPhone(phone: Phone): Phone
    fun updatePhone(phone: Phone): Phone
    fun updatePhones(phones: List<Phone>)
    fun deletePhone(id: Int)
}