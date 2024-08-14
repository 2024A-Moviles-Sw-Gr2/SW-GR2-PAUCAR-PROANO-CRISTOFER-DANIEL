import java.time.LocalDate

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
