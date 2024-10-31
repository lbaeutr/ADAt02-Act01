package org.example

import org.example.Models.Proveedor
import org.example.Repository.HibernateUtils
import org.example.Repository.ProductoRepository
import org.example.Repository.ProveedorRepository
import org.example.Repository.UsuarioRepository
import org.example.Services.ProductoService
import org.example.Services.ProveedorService
import org.example.Services.UsuarioService

fun main() {


    val emf = HibernateUtils.getEntityManagerFactory("unidadMySQL")
    val productoRepository = ProductoRepository(emf)
    val proveedorRepository = ProveedorRepository(emf)
    val usuarioRepository = UsuarioRepository(emf)
    val productoService = ProductoService(productoRepository, proveedorRepository)
    val proveedorService = ProveedorService(proveedorRepository)
    val usuarioService = UsuarioService(usuarioRepository)


    val proveedor1 = Proveedor("Eutimio SL", "Calle de la piruleta")
    val proveedor2 = Proveedor("Pepito SL", "Calle del chupachups")

    proveedorRepository.create(proveedor1)
    proveedorRepository.create(proveedor2)

    var logeo: Boolean= login(usuarioService)

    do {

        println("Seleccione una opción: ")
        println("1. Dar de alta un producto")
        println("2. Dar de baja un producto")
        println("3. Modificar nombre de un producto")
        println("4. Modificar stock de un producto")
        println("5. Obtener un producto")
        println("6. Obtener productos con stock")
        println("7. Obtener productos sin stock")
        println("8. Obtener proveedor de un producto")
        println("9. Obtener todos los proveedores")
        println("10. Salir")

        var opcion = readLine()?.toIntOrNull()
        when (opcion) {
            1 -> productoService.altaProducto()
            2 -> productoService.bajaProducto()
            3 -> productoService.modificarNombreProducto()
            4 -> productoService.modificarStockProducto()
            5 -> productoService.obtenerProducto()
            6 -> productoService.obtenerProductosStock()
            7 -> productoService.obtenerProductosSinStock()
            8 -> productoService.obtenerProveedorProducto()
            9 -> proveedorService.obtenerProvedores()
            10 -> println("Saliendo, espere un momento..")
            else -> println("Opción no válida. Por favor, intente nuevamente.")
        }
    }while (opcion != 10)




}

fun login(usuarioService: UsuarioService): Boolean {
    for (i in 1..3) { // Permite 3 intentos
        println("Seleccione una opción: ")
        println("1. Crear usuario")
        println("2. Acceder con mi usuario")
        println("3. Salir")

        val opcion = readLine()?.toIntOrNull()
        when (opcion) {
            1 -> usuarioService.createUser()
            2 -> usuarioService.comprobarUser()

            3 -> {
                println("Saliendo, espere un momento..")
                return true
            }
            else -> println("Opción no válida. Por favor, intente nuevamente.")
        }
    }
    println("Demasiados intentos fallidos.")
    return true
}



