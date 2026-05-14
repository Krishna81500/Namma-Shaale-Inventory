package com.example.myapplication.data.local

import androidx.room.*
import com.example.myapplication.data.model.Asset
import com.example.myapplication.data.model.ConditionHistory
import com.example.myapplication.data.model.IssueLog
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets")
    fun getAllAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM assets WHERE assetId = :id")
    suspend fun getAssetById(id: Long): Asset?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: Asset): Long

    @Update
    suspend fun updateAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Query("SELECT * FROM issue_logs WHERE assetId = :assetId")
    fun getIssuesForAsset(assetId: Long): Flow<List<IssueLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssue(issue: IssueLog)

    @Update
    suspend fun updateIssue(issue: IssueLog)

    @Query("SELECT * FROM condition_history WHERE assetId = :assetId ORDER BY checkDate DESC")
    fun getConditionHistoryForAsset(assetId: Long): Flow<List<ConditionHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConditionHistory(history: ConditionHistory)
    
    @Query("SELECT COUNT(*) FROM assets")
    fun getAssetCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM issue_logs WHERE repairStatus != 'Resolved'")
    fun getPendingIssueCount(): Flow<Int>

    // Member Management
    @Query("SELECT * FROM members")
    fun getAllMembers(): Flow<List<com.example.myapplication.data.model.Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: com.example.myapplication.data.model.Member)

    @Update
    suspend fun updateMember(member: com.example.myapplication.data.model.Member)

    @Query("SELECT * FROM attendance WHERE memberId = :memberId")
    fun getAttendanceForMember(memberId: Long): Flow<List<com.example.myapplication.data.model.Attendance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: com.example.myapplication.data.model.Attendance)

    // User Authentication
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: com.example.myapplication.data.model.User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): com.example.myapplication.data.model.User?

    @Query("SELECT * FROM users WHERE uniqueId = :uniqueId LIMIT 1")
    suspend fun getUserByUniqueId(uniqueId: String): com.example.myapplication.data.model.User?
}
