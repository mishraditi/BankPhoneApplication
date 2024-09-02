package org.aditilearn.bank.services

import org.aditilearn.bank.datasource.BankDataSource
import org.aditilearn.bank.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService (private val dataSource: BankDataSource) { //MockBankDataSource
    fun getBanks(): Collection<Bank>{
        return dataSource.retrieveBanks()
    }

    fun getBank(accountNumber: String): Bank {
        return dataSource.retrieveBank(accountNumber)
    }

    fun addBank(bank: Bank): Bank =
            dataSource.createBank(bank)

    fun updateBank(bank: Bank): Bank =
      dataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) :Unit =
   dataSource.deleteBank(accountNumber)

}
//fun doo() {
//    val dataSource: BankDataSource = MockBankDataSource() //correct
//    val secondSource: MockBankDataSource = dataSource

//    val variable: SuperClass = object of Subclass() then fine
//    val varible: Subclass = object of subclass by referenced as Superclass
//    gives error as it is giving in above two cases
//}