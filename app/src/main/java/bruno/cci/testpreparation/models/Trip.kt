package bruno.cci.testpreparation.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Trip: RealmObject() {
    @PrimaryKey
    var title: String? = null
    var dateStart: Long? = null
    var dateEnd: Long? = null
    var destination: String? = null
    var inFavorite: Boolean? = false
}