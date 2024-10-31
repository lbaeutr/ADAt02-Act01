package org.example.Models
import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
class Usuario (

    @Column(name = "contraseña", nullable = false, length = 20)
    val pass : String?,

    @Id
    @Column(name = "nombre_usuario", nullable = false)
    val nameUser : String?,
){
    constructor() : this(null, null)
}