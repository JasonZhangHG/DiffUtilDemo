package cool.diffutil.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): LiveData<MutableList<User>>?

    @Query("SELECT * FROM user")
    fun getAll2(): MutableList<User>?

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneUser(users: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserList(list: List<User>)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user ORDER BY last_name ASC")
    fun usersByLastName(): LiveData<List<User>>?

}
