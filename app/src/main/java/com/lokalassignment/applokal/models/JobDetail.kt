package com.lokalassignment.applokal.models

import com.google.gson.annotations.SerializedName

data class JobDetail(

    val id: Int?,
    val title: String?,
    val type: Int,

    val creatives: List<Creative>?,

    @SerializedName("job_role")
    val jobRole: String?,

    @SerializedName("primary_details")
    val primaryDetails: PrimaryDetails?,

    @SerializedName("company_name")
    val companyName: String?,

    @SerializedName("custom_link")
    val customLink: String?,

    val views: String?,

    @SerializedName("contact_preference")
    val contactPreference: Contact?,

    @SerializedName("expire_on")
    val expireOn: String?,

    @SerializedName("created_on")
    val createdOn: String?,

    @SerializedName("job_hours")
    val jobHours: String?,

    @SerializedName("openings_count")
    val openingsCount: Int?,

    @SerializedName("job_category")
    val jobCategory: String?,

    @SerializedName("other_details")
    val otherDetails: String?,

    @SerializedName("contentV3")
    val content: ContentV3?
)

data class Creative (
    @SerializedName("image_url")
    val imageUrl: String?
)

data class Contact(
    @SerializedName("whatsapp_link")
    val whatsappLink: String?
)


data class PrimaryDetails(
    @SerializedName("Place")
    val place: String?,
    @SerializedName("Salary")
    val salary: String?,
    @SerializedName("Experience")
    val experience: String?,
    @SerializedName("Qualification")
    val qualification: String?
)

data class ContentV3(
    val V3: List<Field>
)

data class Field(
    @SerializedName("field_key") val fieldKey: String,
    @SerializedName("field_value") val fieldValue: String
)
