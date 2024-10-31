package org.example.Repository

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.example.Models.Producto
import org.example.Models.Proveedor

class ProductoRepository(emf: EntityManagerFactory) {


    fun create(producto: Producto) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            em.persist(producto)
            em.transaction.commit()
        } catch (e: Exception) {
            em.transaction.rollback()
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun read(id: String): Producto? {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            em.find(Producto::class.java, id)
        } finally {
            HibernateUtils.closeEntityManager(em)
        }

    }

    fun readProveedorProducto(id: String): Proveedor? {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            val producto = em.find(Producto::class.java, id)
            producto.proveedor
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun readProductoSinStock(): List<Producto> {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            // JPQL query para buscar productos cuyo stock sea 0
            //Lenguaje de consulta de objetos Java Persistence Query Language (JPQL), para objetos persistentes.
            // busca productos cuyo stock sea 0
            val query = em.createQuery("SELECT p FROM Producto p WHERE p.stock = 0", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun readProductoConStock(): List<Producto> {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            // Igual que el anterior pero con stock mayor a 0
            val query = em.createQuery("SELECT p FROM Producto p WHERE p.stock > 0", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun readAll(): List<Producto> {
        //createquery es para hacer consultas en la base de datos con JPQL, el createquery funciona como un select * from tabla en SQL.
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        return try {
            val query = em.createQuery("SELECT p FROM Producto p", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun updateProductoNombre(id: String, nombreNuevo: String) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            val producto = read(id)
            if (producto != null) {
                producto.nombre = nombreNuevo
                em.transaction.begin()
                em.merge(producto)
                em.transaction.commit()
            }
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }


    }

    fun updateProductoStock(id: String, stockNuevo: Int) {
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            val producto = read(id)
            if (producto != null) {
                producto.stock = stockNuevo
                em.transaction.begin()
                em.merge(producto)
                em.transaction.commit()
            }
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            HibernateUtils.closeEntityManager(em)
        }
    }

    fun delete(id: String){
        val em: EntityManager = HibernateUtils.getEntityManager("unidadMySQL")
        try {
            em.transaction.begin()
            val producto = em.find(Producto::class.java, id)
            if (producto != null) {
                em.remove(producto)
                em.transaction.commit()
            } else {
                em.transaction.rollback()
            }
        } catch (e: Exception) {
          em.transaction.rollback()
            println("No ha sido posible encontrar el producto con id $id")

        }finally {
            HibernateUtils.closeEntityManager(em)
        }

    }
}