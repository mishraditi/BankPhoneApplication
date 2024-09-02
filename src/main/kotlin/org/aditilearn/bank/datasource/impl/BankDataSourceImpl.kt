package org.aditilearn.bank.datasource.impl

import org.aditilearn.bank.datasource.BankDataSource
import org.aditilearn.bank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class BankDataSourceImpl : BankDataSource {
    val banks = mutableListOf(
            Bank("1234" , 3.14 , 17),
            Bank("1290" , 5.14 , 90),
            Bank("7382" , 9.14 , 37))

   override fun retrieveBanks() : Collection<Bank> {
       return banks
//       return listOf(Bank("" , 0.0 , 1))
   }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.first{it.accountNumber == accountNumber }
    }

    override fun createBank(bank: Bank): Bank {
        if(banks.any { it.accountNumber == bank.accountNumber }) {
       throw IllegalArgumentException ("bank with account number ${bank.accountNumber} already exits")
        }
     banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
      val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
              ?:throw NoSuchElementException(" could not find a bank with account number ")
        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }
    override fun deleteBank(accountNumber: String) {
        val currentBank = banks.firstOrNull { it.accountNumber == accountNumber }
                ?:throw NoSuchElementException(" could not find a bank with account number ")
        banks.remove(currentBank)
    }
}


//model(structure define) (class) -> DataSource ( Keeps the objects of model) -> Service -> Controller

// -> Model
// -> @Repository[DataSource] fun getBankById(id: Int) <- id from service
// -> @Service[Service] fun getBank(bankId) <- bankId = 1234 ( from controller from url)
// -> @RestController[Controller] ("/api/banks/1234")