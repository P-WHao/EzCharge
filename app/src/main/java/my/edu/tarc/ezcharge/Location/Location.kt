package my.edu.tarc.ezcharge.Location

data class Location(
    var locationName : String ?= null,
    var locationAddress : String ?= null,
    var img: String ?=null,

    var distance : Float ?= null,
    var locationContact: String? = null,
    var locationGeo: String? = null

)