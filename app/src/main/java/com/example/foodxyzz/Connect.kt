package com.example.foodxyzz

import com.example.foodxyzz.contract.authentication.loginRequest3
import com.example.foodxyzz.contract.authentication.registerRequest
import com.example.foodxyzz.contract.invoices.AddInvoiceRequest
import com.example.foodxyzz.models.EntityInv
import com.example.foodxyzz.models.EntityMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.w3c.dom.Entity
import java.net.HttpURLConnection
import java.net.URL

class Connect {
    companion object {
        const val BASE_URL = "http://192.168.137.79:8080"
        var token = ""

        fun login(loginRequest : loginRequest3): Boolean{
            val url = URL("${BASE_URL}/api/auth/login")

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")

                val gson = Gson()
                val body = gson.toJson(loginRequest)
                with(outputStream.bufferedWriter()){
                    write(body.toString())
                    flush()
                }

                when(responseCode){
                    200 -> {
                        val data = JSONObject(inputStream.bufferedReader().readText()).getJSONObject("data")
                        token = data["token"].toString()
                        return true

                    }
                    else -> return false
                }
            }
        }

        fun register(regRequest: registerRequest): Boolean{
            val url = URL("${BASE_URL}/api/auth/register")
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")

                val gson = Gson()
                val body = gson.toJson(regRequest)
                with(outputStream.bufferedWriter()){
                    write(body.toString())
                    flush()
                }

                when(responseCode){
                    200 -> {
                        val data = JSONObject(inputStream.bufferedReader().readText()).getJSONObject("data")
                        token = data["token"].toString()
                        return true

                    }
                    else -> return false
                }
            }
        }

        fun getMenu() : ArrayList<EntityMenu> {
            val url = URL("${BASE_URL}/api/product")
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer ${token}")

                val gson = Gson()
                val entityMenu : ArrayList<EntityMenu> = arrayListOf()
                when(responseCode){
                    200 -> {
                        val responses = JSONObject(inputStream.bufferedReader().readText())
                        val data = responses.getJSONArray("data")
                        val myType = object : TypeToken<ArrayList<EntityMenu>>() {}.type
                        entityMenu.addAll(gson.fromJson(data.toString(), myType))
                        return entityMenu
                    }
                    else -> return entityMenu
                }
            }
        }

        fun saveInvoice(request: AddInvoiceRequest) : EntityInv?{
            val url = URL("${BASE_URL}/api/Invoice")
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer ${token}")

                val gson = Gson()
                with(outputStream.bufferedWriter()){
                    write(gson.toJson(request).toString())
                    flush()
                }
                when(responseCode){
                    200 -> {
                        val responses = JSONObject(inputStream.bufferedReader().readText())
                        val entityInv = gson.fromJson(responses.toString(), EntityInv::class.java)
                        return entityInv
                    }
                    else -> return null
                }
            }
        }
    }
}