package org.aditilearn.bank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import org.aditilearn.bank.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.web.bind.annotation.GetMapping

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
        val mockMvc: MockMvc,  //http
        var objectMapper: ObjectMapper //convert into json object
) {
//relevant for all
//val baseUrl = "/api/banks"        we replace all api/banks with baseurl

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks() {
        @Test
        fun `should return all banks`() {
            //when    //then
            mockMvc.get("/api/banks")
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].accountNumber ") { value("1234") }
                    } }
    }

    @Test
    fun `should return the bank with given account number`() {
        //given
        val accountNumber = 1234

        //when
        mockMvc.get("/api/banks/$accountNumber")
                //then
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("17") }
                } }

    @Test
    fun `should return NOT FOUND  if the account number does not exists `() {
        //Given
        val accountNumber = "Does_not_exists"
        //When /Then
        mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
    }

    @Nested
    @DisplayName("POST/api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new banks`() {
            //given
            val newBank = Bank(accountNumber = "acc123", trust = 98.89, transactionFee = 2)

            // when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            //        then
            performPost
                    .andDo { print() }
                    .andExpect {
                        status { isCreated() } // 201 bank is created
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.accountNumber") { value("acc123") }
                        jsonPath("$.trust") { value("98.89") }
                        jsonPath("$.transactionFee") { value("2") }
                    }
        }

        @Test
        fun `should return BAD REQUETS if bank with given account number already exists `() {
            //given
            val invalidBank = Bank("1234", 1.0, 10)
            //when
            val performPost = mockMvc.post("/api/banks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            performPost
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }
        }

        @Nested
        @DisplayName("PATCH/api/banks")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class PatchExistingBank {
            @Test
            fun `should update an existing bank`() {
                //given
                val updateBank = Bank("1234", 1.0, 10)
                //when
                val performPatchRequest = mockMvc.patch("/api/banks") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(updateBank)
                }
                performPatchRequest
                        .andDo { print() }
                        .andExpect {
                            status { isOk() }
                            content {
                                contentType(MediaType.APPLICATION_JSON)
                                json(objectMapper.writeValueAsString(updateBank))
                            }

                mockMvc.get("/api/banks/${updateBank.accountNumber}")
                        .andExpect { content { json(objectMapper.writeValueAsString(updateBank)) } }
            }

            @Test
            fun `should return BAD REQUEST if no bank with given  account number exists `() {
                val invalidBank = Bank("does_not_exits", 1.0, 1)
                val performPatchRequest = mockMvc.patch("/api/banks") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(invalidBank)
                }
                performPatchRequest.andDo { print() }
                        .andExpect { status { isNotFound() } }
            }
        }
        @Nested
        @DisplayName("DELETE/api/banks/{accountNumber")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class DeleteExistingBank {
            @Test
            fun `should delete the bank with given account number `(){
                //GIVEN
                val accountNumber = 1234
            //WHEN //then
                mockMvc.delete("/api/banks/$accountNumber")
                        .andDo { print() }
                        .andExpect {
                            status { isNoContent() }
                        }
                mockMvc.get("/api/banks/$accountNumber")
                        .andExpect { status { isNotFound() }}
            }
            @Test
            fun`should return NO FOUND if no bank with given account number `(){
             val invalidAccountNumber  = "does_not_exists"
                mockMvc.delete("/api/banks/$invalidAccountNumber")
                        .andDo { print() }
                        .andExpect { status { isNotFound() } }
            }
        }
    }
}}