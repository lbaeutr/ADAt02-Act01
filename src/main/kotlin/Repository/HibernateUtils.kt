package org.example.Repository

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object HibernateUtils {

    private lateinit var emf: EntityManagerFactory
    /*
    object HibernateUtils: Define un singleton en Kotlin.
    Esto significa que esta clase solo tendrá una instancia en toda la aplicación.

    private lateinit var emf: EntityManagerFactory: Declara una variable emf que se inicializará más adelante.
    lateinit indica que se inicializará en un momento posterior antes de ser usada.
     */



    // Método para obtener un EntityManagerFactory
     fun getEntityManagerFactory(namePersistenceUnit: String = ""): EntityManagerFactory {
        return if(this::emf.isInitialized && emf.isOpen) {
            emf;
        } else {
            Persistence.createEntityManagerFactory(namePersistenceUnit)
        }
    }

    fun getEntityManager(namePersistenceUnit: String = ""): EntityManager {
        return getEntityManagerFactory(namePersistenceUnit).createEntityManager()
    }


    // Método para cerrar todos los EntityManagerFactory
    fun shutdown() {
        if (emf.isOpen) {
            emf.close()
        }
    }

    // Método para cerrar un EntityManager específico
    fun closeEntityManager(em: EntityManager?) {
        try {
            if (em != null && em.isOpen) {
                em.close()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }



}