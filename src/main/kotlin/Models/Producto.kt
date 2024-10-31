package org.example.Models

import jakarta.persistence.*
import java.time.Instant
import java.util.Date


@Entity
@Table(name = "productos")
class Producto (

    @Column(name = "categoria")
    val categoria: String?,

    @Column(name = "nombre", nullable = false)
    var nombre: String?,

    @Column(name = "descripcion")
    val descripcion: String?,

    @Column(name = "precio_sin_iva")
    val precio_sin_iva: Float?,

    @Column(name = "precio_con_iva")
    val precio_con_iva: Float?,

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    val fechaAlta: Date? = Date.from(Instant.now()),

    @Column(name = "stock")
    var stock: Int?,

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinColumn(name = "id_proveedor")
    var proveedor: Proveedor?,

    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String?,
){

    constructor() : this(null, null, null, null, null, null, null, null, null)

    /*
    El constructor secundario se encarga de calcular el precio con IVA y de generar un ID para el producto,
    sin necesidad de que el usuario lo proporcione.
    También se encarga de asignar el producto al proveedor, con las tres primeras letras de cada atributo.
     */

    constructor(categoria: String, nombre: String, descripcion: String, precioSinIva: Float, stock: Int, proveedor: Proveedor)
            : this(categoria, nombre, descripcion, precioSinIva,precioSinIva*1.21f, Date.from(Instant.now()), stock,
        proveedor,"${categoria.take(3)}${nombre.take(3)}${proveedor.nombre?.take(3)}")


    override fun toString(): String {
        return "Producto(id='$id', categoria='$categoria', nombre='$nombre', descripcion='$descripcion', " +
                "precioSinIva=$precio_sin_iva, precioConIva=$precio_con_iva, fechaAlta=$fechaAlta, stock=$stock, " +
                "proveedor=${proveedor?.nombre})"
    }
}