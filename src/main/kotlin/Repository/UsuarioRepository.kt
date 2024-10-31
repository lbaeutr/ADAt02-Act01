package org.example.Repository

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.example.Models.Usuario

class UsuarioRepository(emf: EntityManagerFactory) {

    fun create(user : Usuario) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")

        try {
            em.transaction.begin()
            em.persist(user)
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun read(id: String) : Usuario? {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            em.find(Usuario::class.java, id)
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun readAll(): List<Usuario> {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            // Esto esta haciendo un select *
            val query = em.createQuery("SELECT u FROM Usuario u", Usuario::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun update(user: Usuario) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            em.merge(user)
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun delete(id: String) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            val user = em.find(Usuario::class.java, id)
            if (user != null) {
                em.remove(user)
                em.transaction.commit()
            } else {
                em.transaction.rollback()
                println("No se encontró el usuario con id $id")
            }
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

}
