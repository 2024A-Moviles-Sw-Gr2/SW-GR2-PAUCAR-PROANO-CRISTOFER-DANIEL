import java.time.LocalDate

data class Empresa(
    var id: Int,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    var fechaFundacion: LocalDate,
    val departamentos: MutableList<Departamento> = mutableListOf()
)
