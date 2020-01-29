package bruno.cci.testpreparation.models

import com.vicpin.krealmextensions.AutoIncrementPK
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












open class Game: RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var dateSortie: Long? = null
    var titre: Long? = null
    var editeur: String? = null
    var multijoueur: Boolean? = false
}