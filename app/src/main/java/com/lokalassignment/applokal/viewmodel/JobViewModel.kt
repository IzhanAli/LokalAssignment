package com.lokalassignment.applokal.viewmodel

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.lokalassignment.applokal.data.JobsDatabase
import com.lokalassignment.applokal.models.JobDetail
import com.lokalassignment.applokal.models.JobEntity
import com.lokalassignment.applokal.retrofit.RetrofitInstance
import com.lokalassignment.applokal.util.JobDAO
import com.lokalassignment.applokal.util.JobPagingSource
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException


class JobViewModel(application: Application): AndroidViewModel(application){


    private val jobDao: JobDAO = JobsDatabase.getDatabase(application).jobDAO()

    private val _selectedJob = MutableLiveData<JobDetail>()
    val selectedJob: LiveData<JobDetail> get() = _selectedJob

    fun selectJob(job: JobDetail) {
        _selectedJob.value = job
    }

    private val _saved = MutableLiveData<ArrayList<JobDetail>?>()
    val saved: LiveData<ArrayList<JobDetail>?> get() = _saved
    init {
        //fetchListings()
        fetchSaved()
        Log.d("JobViewModel", "JobDao initialized: $jobDao")

    }
    private val pager = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { JobPagingSource(RetrofitInstance.api) }
    )

    val jobFlow = pager.flow.cachedIn(viewModelScope)




    fun saveJob(job: JobDetail) {
        viewModelScope.launch {
            try {
                val jobId = job.id!!
                val jobEntity = JobEntity(id = jobId, value = Gson().toJson(job))
                if (jobDao.isJobSaved(jobId)){
                    jobDao.removeJob(jobEntity)
                }else{
                    jobDao.insertJob(jobEntity)

                }
            }catch (e: Exception){
                Log.e("Error db", e.message.toString())
            }
            val list: ArrayList<JobDetail> = ArrayList()
            val raw = jobDao.getAllJobs()
            for(i in raw){
                val obj = Gson().fromJson(i.value, JobDetail::class.java)
                list.add(obj)
            }

            _saved.postValue(list)
        }
    }

    fun isJobSaved(jobId: Int): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.postValue(jobDao.isJobSaved(jobId))
        }
        return result
    }


    private fun fetchSaved() {
        viewModelScope.launch {
            val list: ArrayList<JobDetail> = ArrayList()
            val raw = jobDao.getAllJobs()
            for(i in raw){
                val obj = Gson().fromJson(i.value, JobDetail::class.java)
                list.add(obj)
            }

            _saved.value = list
        }
    }



}
