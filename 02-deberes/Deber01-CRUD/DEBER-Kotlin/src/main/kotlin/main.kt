import java.io.File
import java.time.LocalDate

data class Empresa(
    var id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var fechaFundacion: LocalDate,
    val departamentos: MutableList<Departamento> = mutableListOf()
)

data class Departamento(
    var id: Int,
    var empresaId: Int,
    var nombre: String,
    var numeroEmpleados: Int,
    var presupuesto: Double,
    var estaActivo: Boolean
)

class AplicacionCRUD(private val empresasArchivo: String, private val departamentosArchivo: String) {

    private val empresas: MutableList<Empresa> = mutableListOf()
    private val departamentos: MutableList<Departamento> = mutableListOf()

    init {
        crearArchivosSiNoExisten()
        cargarDatos()
    }

    private fun crearArchivosSiNoExisten() {
        listOf(empresasArchivo, departamentosArchivo).forEach {
            val file = File(it)
            if (!file.exists()) file.createNewFile()
        }
    }

    private fun cargarDatos() {
        cargarEmpresas()
        cargarDepartamentos()
    }

    private fun cargarEmpresas() {
        File(empresasArchivo).useLines { lines ->
            lines.map { it.split(",") }
                .filter { it.size == 5 }
                .forEach { campos ->
                    empresas.add(Empresa(
                        id = campos[0].toInt(),
                        nombre = campos[1],
                        direccion = campos[2],
                        telefono = campos[3],
                        fechaFundacion = LocalDate.parse(campos[4])
                    ))
                }
        }
    }

    private fun cargarDepartamentos() {
        File(departamentosArchivo).useLines { lines ->
            lines.map { it.split(",") }
                .filter { it.size == 6 }
                .forEach { campos ->
                    val departamento = Departamento(
                        id = campos[0].toInt(),
                        empresaId = campos[1].toInt(),
                        nombre = campos[2],
                        numeroEmpleados = campos[3].toInt(),
                        presupuesto = campos[4].toDouble(),
                        estaActivo = campos[5].toBoolean()
                    )
                    departamentos.add(departamento)
                    empresas.find { it.id == departamento.empresaId }?.departamentos?.add(departamento)
                }
        }
    }

    private fun guardarEmpresasEnArchivo() {
        File(empresasArchivo).printWriter().use { out ->
            empresas.forEach { empresa ->
                out.println("${empresa.id},${empresa.nombre},${empresa.direccion},${empresa.telefono},${empresa.fechaFundacion}")
            }
        }
    }

    private fun guardarDepartamentosEnArchivo() {
        File(departamentosArchivo).printWriter().use { out ->
            departamentos.forEach { departamento ->
                out.println("${departamento.id},${departamento.empresaId},${departamento.nombre},${departamento.numeroEmpleados},${departamento.presupuesto},${departamento.estaActivo}")
            }
        }
    }

    fun crearEmpresa(nombre: String, direccion: String, telefono: String, fechaFundacion: LocalDate) {
        val id = (empresas.maxOfOrNull { it.id } ?: 0) + 1
        empresas.add(Empresa(id, nombre, direccion, telefono, fechaFundacion))
        guardarEmpresasEnArchivo()
    }

    fun crearDepartamento(empresaId: Int, nombre: String, numeroEmpleados: Int, presupuesto: Double, estaActivo: Boolean) {
        val empresa = empresas.find { it.id == empresaId }
        if (empresa != null) {
            val id = (departamentos.maxOfOrNull { it.id } ?: 0) + 1
            val departamento = Departamento(id, empresaId, nombre, numeroEmpleados, presupuesto, estaActivo)
            departamentos.add(departamento)
            empresa.departamentos.add(departamento)
            guardarDepartamentosEnArchivo()
        } else {
            println("Empresa no encontrada.")
        }
    }

    fun actualizarEmpresa(id: Int, nombre: String, direccion: String, telefono: String, fechaFundacion: LocalDate) {
        val empresa = empresas.find { it.id == id }
        if (empresa != null) {
            empresa.nombre = nombre
            empresa.direccion = direccion
            empresa.telefono = telefono
            empresa.fechaFundacion = fechaFundacion
            guardarEmpresasEnArchivo()
            println("Empresa actualizada exitosamente.")
        } else {
            println("Empresa no encontrada.")
        }
    }

    fun actualizarDepartamento(id: Int, nombre: String, numeroEmpleados: Int, presupuesto: Double, estaActivo: Boolean) {
        val departamento = departamentos.find { it.id == id }
        if (departamento != null) {
            departamento.nombre = nombre
            departamento.numeroEmpleados = numeroEmpleados
            departamento.presupuesto = presupuesto
            departamento.estaActivo = estaActivo
            guardarDepartamentosEnArchivo()
            println("Departamento actualizado exitosamente.")
        } else {
            println("Departamento no encontrado.")
        }
    }

    fun quitarDepartamentoDeEmpresa(departamentoId: Int) {
        val departamento = departamentos.find { it.id == departamentoId }
        if (departamento != null) {
            val empresa = empresas.find { it.id == departamento.empresaId }
            empresa?.departamentos?.remove(departamento)
            departamentos.remove(departamento)
            guardarDepartamentosEnArchivo()
        } else {
            println("Departamento no encontrado.")
        }
    }

    fun leerDepartamentosDeEmpresa(empresaId: Int): List<Departamento>? {
        return empresas.find { it.id == empresaId }?.departamentos
    }

    fun listarEmpresas() {
        println("Empresas disponibles:")
        empresas.forEach { println("ID: ${it.id}, Nombre: ${it.nombre}") }
    }

    fun listarDepartamentos() {
        println("Departamentos disponibles:")
        departamentos.forEach { println("ID: ${it.id}, Nombre: ${it.nombre}, Empresa ID: ${it.empresaId}") }
    }
}

fun main() {
    val aplicacion = AplicacionCRUD("empresas.txt", "departamentos.txt")

    var opcion: Int
    do {
        println("Menu:")
        println("1. Crear Empresa")
        println("2. Agregar Departamento")
        println("3. Actualizar Empresa")
        println("4. Actualizar Departamento")
        println("5. Quitar Departamento")
        println("6. Listar Departamentos de Empresa")
        println("7. Salir")
        println("Ingrese su opcion:")
        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                println("Ingrese nombre de la empresa:")
                val nombre = readLine() ?: ""
                println("Ingrese direccion de la empresa:")
                val direccion = readLine() ?: ""
                println("Ingrese telefono de la empresa:")
                val telefono = readLine() ?: ""
                val fechaFundacion = LocalDate.now()
                aplicacion.crearEmpresa(nombre, direccion, telefono, fechaFundacion)
                println("Empresa creada exitosamente.")
            }
            2 -> {
                aplicacion.listarEmpresas()
                println("Ingrese ID de la empresa:")
                val empresaId = readLine()?.toIntOrNull() ?: -1
                if (empresaId == -1) {
                    println("ID de empresa inválido.")
                    continue
                }
                println("Ingrese nombre del departamento:")
                val nombre = readLine() ?: ""
                println("Ingrese número de empleados del departamento:")
                val numeroEmpleados = readLine()?.toIntOrNull() ?: 0
                println("Ingrese presupuesto del departamento:")
                val presupuesto = readLine()?.toDoubleOrNull() ?: 0.0
                println("¿El departamento está activo? (true/false):")
                val estaActivo = readLine()?.toBoolean() ?: false
                aplicacion.crearDepartamento(empresaId, nombre, numeroEmpleados, presupuesto, estaActivo)
                println("Departamento creado exitosamente.")
            }
            3 -> {
                aplicacion.listarEmpresas()
                println("Ingrese ID de la empresa a actualizar:")
                val empresaId = readLine()?.toIntOrNull() ?: -1
                if (empresaId == -1) {
                    println("ID de empresa inválido.")
                    continue
                }
                println("Ingrese nuevo nombre de la empresa:")
                val nombre = readLine() ?: ""
                println("Ingrese nueva direccion de la empresa:")
                val direccion = readLine() ?: ""
                println("Ingrese nuevo telefono de la empresa:")
                val telefono = readLine() ?: ""
                val fechaFundacion = LocalDate.now()
                aplicacion.actualizarEmpresa(empresaId, nombre, direccion, telefono, fechaFundacion)
            }
            4 -> {
                aplicacion.listarDepartamentos()
                println("Ingrese ID del departamento a actualizar:")
                val departamentoId = readLine()?.toIntOrNull() ?: -1
                if (departamentoId == -1) {
                    println("ID de departamento inválido.")
                    continue
                }
                println("Ingrese nuevo nombre del departamento:")
                val nombre = readLine() ?: ""
                println("Ingrese nuevo número de empleados del departamento:")
                val numeroEmpleados = readLine()?.toIntOrNull() ?: 0
                println("Ingrese nuevo presupuesto del departamento:")
                val presupuesto = readLine()?.toDoubleOrNull() ?: 0.0
                println("¿El departamento está activo? (true/false):")
                val estaActivo = readLine()?.toBoolean() ?: false
                aplicacion.actualizarDepartamento(departamentoId, nombre, numeroEmpleados, presupuesto, estaActivo)
            }
            5 -> {
                aplicacion.listarDepartamentos()
                println("Ingrese ID del departamento:")
                val departamentoId = readLine()?.toIntOrNull() ?: -1
                if (departamentoId == -1) {
                    println("ID de departamento inválido.")
                    continue
                }
                aplicacion.quitarDepartamentoDeEmpresa(departamentoId)
                println("Departamento eliminado exitosamente.")
            }
            6 -> {
                aplicacion.listarEmpresas()
                println("Ingrese ID de la empresa:")
                val empresaId = readLine()?.toIntOrNull() ?: -1
                if (empresaId == -1) {
                    println("ID de empresa inválido.")
                    continue
                }
                val departamentos = aplicacion.leerDepartamentosDeEmpresa(empresaId)
                if (departamentos != null && departamentos.isNotEmpty()) {
                    println("Departamentos de la Empresa $empresaId:")
                    departamentos.forEach { println(it) }
                } else {
                    println("Empresa no encontrada o sin departamentos.")
                }
            }
            7 -> println("Saliendo del programa...")
            else -> println("Opción inválida. Inténtelo de nuevo.")
        }
    } while (opcion != 7)
}
