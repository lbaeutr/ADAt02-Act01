package org.example.Services

import org.example.Models.Producto
import org.example.Repository.ProductoRepository
import org.example.Repository.ProveedorRepository

class ProductoService(val productoRepository: ProductoRepository, val proveedorRepository: ProveedorRepository) {


    fun altaProducto() {
        println("Intro ID proveedor: ")
        val idProveedor = readLine()!!.toLong()
        val proveedor = proveedorRepository.read(idProveedor)

        if (proveedor != null) {
            println("Intro nombre producto: ")
            val nombreProducto = readLine()!!
            println("introducir categoria: ")
            val categoria = readLine()!!
            println("introducir descripcion de producto: ")
            val descripcion = readLine()!!
            println("introducir precio sin iva: ")
            val precioSinIva = readLine()!!.toFloat()
            println("introducir stock: ")
            val stock = readLine()!!.toInt()


            val nuevoProducto = Producto(categoria, nombreProducto, descripcion, precioSinIva, stock, proveedor)

            proveedor.addProducto(nuevoProducto)
            proveedorRepository.update(proveedor)
        } else {
            println("No existe ese proveedor con esa ID, comprueba la ID")
        }
    }



    fun bajaProducto() {
        println("Introduce ID del producto: ")
        val idProducto = readLine()!!

        val producto = productoRepository.read(idProducto)

        if (producto != null) {
            val proveedor = producto.proveedor

            if (proveedor != null) {
                proveedor.removeProducto(producto)
                proveedorRepository.update(proveedor)
            }

            productoRepository.delete(idProducto)
            println("Producto eliminado correctamente.")
        } else {
            println("Producto no encontrado.")
        }
    }

    fun modificarNombreProducto(){
        println("introduce la id del producto que desees modificar: ")
        val idProducto = readLine()!!
        println("introduce el nuevo nombre del producto: ")
        val nuevoNombre = readLine()!!

        productoRepository.updateProductoNombre(idProducto, nuevoNombre)
    }

    fun modificarStockProducto(){
        println("Introduce la id del producto a modificar: ")
        val idProducto = readLine()!!
        println("Intro nueva cantidad de stock disponible: ")
        val stock = readLine()!!.toInt()

        productoRepository.updateProductoStock(idProducto, stock)
    }

    fun obtenerProducto(){
        println("Introduce la id del producto a obtener: ")
        val idProducto = productoRepository.read(readLine()!!)
        if (idProducto != null){
            println("Producto encontrado: ${idProducto.nombre}, Descripción: ${idProducto.descripcion}, ID: ${idProducto.id}")
        } else {
            println("Producto no encontrado.")
        }
    }

    fun obtenerProductosStock(){

        val productos = productoRepository.readProductoConStock()

        println("Cantidad total de stock: ${productos.size}")

        if (productos.isNotEmpty()){
            println("Productos con stock:")
            productos.forEach { println(it) }
        }else{
            println("No hay productos con stock")
        }
    }


    fun obtenerProductosSinStock(){
        val productos = productoRepository.readProductoSinStock()

        for (producto in productos){
            println(producto)
        }
    }

    fun obtenerProveedorProducto(){
        println("Introduzca ID del producto:")

        val idProducto = readLine()!!

        val proveedor = productoRepository.readProveedorProducto(idProducto)

        if(proveedor != null) {
            println(proveedor)
        }else{
            println("No hay ningún proveedor con ese producto")
        }

    }



}