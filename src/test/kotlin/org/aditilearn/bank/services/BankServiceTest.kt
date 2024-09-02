//package org.aditilearn.bank.services
//
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.aditilearn.bank.datasource.mock.BankDataSource
//import org.aditilearn.bank.datasource.mock.mock.MockBankDataSource
//import org.junit.jupiter.api.Test
//import kotlin.math.truncate
//
//class BankServiceTest{
//    private val dataSource: BankDataSource = MockBankDataSource()
//
//   val bankService =  BankService(dataSource)
//
//    @Test
//    fun`should call its data source to retrieve banks`(){
//        //given
//
//        //if you have such a mock you have to specify its behavior fro every method
//        // call that you expect on it
//
//      every{ dataSource.retrieveBanks() } returns emptyList()
//        //instead of this we also add parameter over the mock function
//
//     //when
//     val banks = bankService.getBanks()
//
//        //then
//        verify(exactly = 1){ dataSource.retrieveBanks()}
//
//
//     }
//}