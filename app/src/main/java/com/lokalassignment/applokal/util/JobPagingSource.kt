package com.lokalassignment.applokal.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lokalassignment.applokal.models.JobDetail

class JobPagingSource(
    private val api: JobsAPI
) : PagingSource<Int, JobDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JobDetail> {
        return try {
            val page = params.key ?: 1 // Start with page 1
            val response = api.getListings(page)

            if (response.isSuccessful && response.body() != null) {
                val jobs = response.body()?.results ?: emptyList()
                LoadResult.Page(
                    data = jobs,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (jobs.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Error fetching jobs"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, JobDetail>): Int? {
        // This is called when refreshing the list
        return state.anchorPosition
    }
}
