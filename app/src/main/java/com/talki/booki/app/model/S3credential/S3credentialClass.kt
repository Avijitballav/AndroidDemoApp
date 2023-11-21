package com.talki.booki.app.model.S3credential

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class S3credentialClass {
    @SerializedName("AWS_S3_KEY")
    @Expose
    var awsS3Key: String? = null

    @SerializedName("AWS_S3_SECRET")
    @Expose
    var awsS3Secret: String? = null

    @SerializedName("AWS_REGION")
    @Expose
    var awsRegion: String? = null

    @SerializedName("AWS_ACL")
    @Expose
    var awsAcl: String? = null

    @SerializedName("AWS_BUCKET")
    @Expose
    var awsBucket: String? = null

    @SerializedName("AWS_Path")
    @Expose
    var aWSPath: String? = null

    @SerializedName("ImagePath")
    @Expose
    var imagePath: String? = null

    @SerializedName("ImagePathS3")
    @Expose
    var imagePathS3: String? = null
}