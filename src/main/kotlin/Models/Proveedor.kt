package org.example.Models
import jakarta.persistence.*

@Entity
@Table(name = "proveedores")
class Proveedor (

    @Column(name = "nombre", nullable = false)
    var nombre: String?,

    @Column(name = "direccion")
    val direccion: String?,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "proveedor", orphanRemoval = true, fetch = FetchType.EAGER)//Todo ver

    val productos : MutableList<Producto> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
){
    constructor() : this(null, null)

    fun addProducto(producto: Producto) {
        productos.add(producto)
        producto.proveedor = this  // TODO Referencia producto al proveedor
    }

    fun removeProducto(producto: Producto) {
        productos.remove(producto)
    }


    override fun toString(): String {
        return "$nombre \n Dirección: $direccion\n Id: $id\nProductos: $productos"
    }
}