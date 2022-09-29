package cool.diffutil.android

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey
    var uid: Int,

    @ColumnInfo(name = "first_name")
    var firstName: String?,

    @ColumnInfo(name = "last_name")
    var lastName: String?
) {

    override fun equals(any: Any?): Boolean {
        if (any == null) {
            return false
        }
        val other: User = any as User
        return ((uid == other.uid && (firstName == other.firstName) && (lastName == other.lastName)))
    }
}