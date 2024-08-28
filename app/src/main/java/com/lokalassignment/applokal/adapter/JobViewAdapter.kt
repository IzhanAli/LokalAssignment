package com.lokalassignment.applokal.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.lokalassignment.applokal.R
import com.lokalassignment.applokal.models.JobDetail

class JobAdapter(private val jobs: List<JobDetail>,
                 private val onItemClick: (JobDetail) -> Unit,
                 private val onSaveClick: (JobDetail) -> Unit,
                 private val isJobSaved: (Int, (Boolean) -> Unit) -> Unit) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val role = itemView.findViewById<TextView>(R.id.textView_title)
        val salary = itemView.findViewById<TextView>(R.id.textView_salary)
        val company = itemView.findViewById<TextView>(R.id.textView_company)
        val location = itemView.findViewById<TextView>(R.id.textView_location)
        val callbtn = itemView.findViewById<TextView>(R.id.button_call)
        val wa = itemView.findViewById<TextView>(R.id.button_wa)
        val img = itemView.findViewById<ImageView>(R.id.banner)
        val ui = itemView.findViewById<MaterialCardView>(R.id.cardparent)
        val save = itemView.findViewById<MaterialButton>(R.id.btnsave)


        fun bind(job: JobDetail) {

            if (job.type != 1040) {

                img.visibility = View.GONE
                ui.visibility = View.VISIBLE

                role.text = job.jobRole
                salary.text = job.primaryDetails?.salary
                company.text = job.companyName
                location.text = job.primaryDetails?.place

                callbtn.setOnClickListener {
                    val i = Intent(Intent.ACTION_DIAL)
                    i.data = Uri.parse(job.customLink)
                    startActivity(itemView.context, i, null)
                }

                wa.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(job.contactPreference?.whatsappLink)
                    startActivity(itemView.context, i, null)
                }
                itemView.setOnClickListener {
                    onItemClick(job)
                }


                isJobSaved(job.id!!){ saved ->

                    if (saved) {
                        save.icon = ContextCompat.getDrawable(itemView.context, R.drawable.sharp_bookmark_added_24)
                    } else {
                        save.icon = ContextCompat.getDrawable(itemView.context, R.drawable.twotone_bookmark_24)
                    }

                }
                save.setOnClickListener {
                    onSaveClick(job)
                    if(save.icon == ContextCompat.getDrawable(itemView.context, R.drawable.twotone_bookmark_24)){
                        save.icon = ContextCompat.getDrawable(itemView.context, R.drawable.sharp_bookmark_added_24)
                    }
                    else if(save.icon == ContextCompat.getDrawable(itemView.context, R.drawable.sharp_bookmark_added_24)){
                        save.icon = ContextCompat.getDrawable(itemView.context, R.drawable.twotone_bookmark_24)
                    }
                }

            }else{
                ui.visibility = View.GONE
                Glide.with(itemView).load(job.creatives?.get(0)?.imageUrl).into(img)
                img.visibility = View.VISIBLE

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item_layout, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    override fun getItemCount() = jobs.size
}
