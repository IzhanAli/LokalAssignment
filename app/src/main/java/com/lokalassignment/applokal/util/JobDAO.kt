package com.lokalassignment.applokal.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lokalassignment.applokal.models.JobEntity

@Dao
interface JobDAO  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Delete
    suspend fun removeJob(job: JobEntity)

    @Query("SELECT * FROM myjobs")
    suspend fun getAllJobs(): List<JobEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM myjobs WHERE id = :jobId LIMIT 1)")
    suspend fun isJobSaved(jobId: Int): Boolean
}