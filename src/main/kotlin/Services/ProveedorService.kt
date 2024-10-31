package org.example.Services

import org.example.Repository.ProveedorRepository

class ProveedorService(val repository: ProveedorRepository) {

    fun obtenerProvedores(){

        val proveedores = repository.readAll()

        if (proveedores.isEmpty()) {
            println("No hay proveedores")
        } else {
            proveedores.forEach { println(it) }//todo revissar esto bien.
        }



    }
}