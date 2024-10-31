package org.example.Services

import org.example.Models.Usuario
import org.example.Repository.UsuarioRepository
import org.example.Repository.HibernateUtils

class UsuarioService(val repository: UsuarioRepository) {

    fun createUser() {
        println("Introduce el nombre de usuario: ")
        val nombreUsuario = readLine()
        println("Introduce la contraseña: ")
        val password = readLine()

        if (nombreUsuario != null && password != null) {
            val nuevoUsuario = Usuario(nameUser = nombreUsuario, pass = password)
            val em = HibernateUtils.getEntityManager("unidadMySQL")
            try {
                em.transaction.begin()
                repository.create(nuevoUsuario)
                em.transaction.commit()
            } catch (e: Exception) {
                em.transaction.rollback()
                println("Error al crear el usuario: ${e.message}")
            } finally {
                HibernateUtils.closeEntityManager(em)
            }
        } else {
            println("El nombre de usuario y la contraseña no pueden ser nulos.")
        }
    }


    fun comprobarUser() {
        println("Introduce el nombre del usuario: ")
        val nombreUsuario = readLine().toString()
        println("Introduce la contraseña: ")
        val password = readLine()

        val em = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            val usuarioDudoso = repository.read(nombreUsuario)
            if (usuarioDudoso != null) {
                if (usuarioDudoso.pass == password) {
                    println("Usuario y contraseña correctos")
                } else {
                    println("Contraseña incorrecta")
                }
            } else {
                println("Usuario no encontrado")
            }
        } catch (e: Exception) {
            println("Error al comprobar el usuario: ${e.message}")
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }
}