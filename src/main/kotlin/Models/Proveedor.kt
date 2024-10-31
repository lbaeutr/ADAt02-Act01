package org.example.Models
import jakarta.persistence.*

@Entity
@Table(name = "proveedores")
class Proveedor (

    @Column(name = "nombre", nullable = false)
    var nombre: String?,

    @Column(name = "direccion")
    val direccion: String?,

    /*
      Relación uno a muchos con la tabla productos
        Un proveedor puede tener muchos productos
        Un producto solo puede tener un proveedor
        funciona con cascade = [CascadeType.ALL] para que al borrar un proveedor se borren todos los productos asociados
        mappedBy = "proveedor" indica que la relación es bidireccional
        fetch = FetchType.EAGER indica que se cargaran todos los productos al cargar un proveedor
     */
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "proveedor", orphanRemoval = true, fetch = FetchType.EAGER)

    val productos : MutableList<Producto> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long? = null
){
    constructor() : this(null, null)

    fun addProducto(producto: Producto) {
        productos.add(producto)
        producto.proveedor = this  // Referencia producto al proveedor actual
    }

    fun removeProducto(producto: Producto) {
        productos.remove(producto)
    }


    override fun toString(): String {
        return "$nombre \n Dirección: $direccion\n Id: $id\nProductos: $productos"
    }
}