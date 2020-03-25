package com.tydeya.familycircle.ui.managerpart.editprofile.details

data class EditableFamilyMember(var name: String, var imageData: ByteArray, var birthdate: Long,
                                var workPlace: String, var studyPlace: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditableFamilyMember

        if (name != other.name) return false
        if (!imageData.contentEquals(other.imageData)) return false
        if (birthdate != other.birthdate) return false
        if (workPlace != other.workPlace) return false
        if (studyPlace != other.studyPlace) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + imageData.contentHashCode()
        result = 31 * result + birthdate.hashCode()
        result = 31 * result + workPlace.hashCode()
        result = 31 * result + studyPlace.hashCode()
        return result
    }
}