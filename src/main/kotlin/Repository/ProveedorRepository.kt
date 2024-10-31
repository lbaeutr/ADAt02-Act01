package org.example.Repository

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.example.Models.Proveedor

class ProveedorRepository(emf: EntityManagerFactory) {

    fun create(proveedor : Proveedor) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            em.persist(proveedor)
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun read(id: Long) : Proveedor? {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            em.find(Proveedor::class.java, id)
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun readAll(): List<Proveedor> {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            val query = em.createQuery("SELECT p FROM Proveedor p", Proveedor::class.java)
        query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun update(proveedor: Proveedor) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            em.merge(proveedor)
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun delete(id: Long) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            val proveedor = em.find(Proveedor::class.java, id)
            if (proveedor != null) {
                em.remove(proveedor)
                em.transaction.commit()
            } else {
                em.transaction.rollback()
                println("No se encontró el proveedor con id $id")
            }
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

}