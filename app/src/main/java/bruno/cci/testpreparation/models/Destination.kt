package bruno.cci.testpreparation.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Destination: RealmObject() {
    @PrimaryKey
    var name: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
}