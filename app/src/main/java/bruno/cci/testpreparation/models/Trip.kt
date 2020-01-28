package bruno.cci.testpreparation.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Trip: RealmObject() {
    @PrimaryKey
    var title: String? = null
    var dateStart: Long? = null
    var dateEnd: Long? = null
    var destination: Destination? = null
    var inFavorite: Boolean? = false

    override fun toString(): String {
        return "Trip(title=$title, dateStart=$dateStart, dateEnd=$dateEnd, destination=$destination, inFavorite=$inFavorite)"
    }
}

