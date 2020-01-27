package bruno.cci.testpreparation.ui.models

data class Trip(
    val title: String,
    val dateStart: Long,
    val dateEnd: Long,
    val destination: String
)