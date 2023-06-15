package com.example.earsproject.data.remote.ears

import com.google.gson.annotations.SerializedName

data class GetAllHospitalsResponse(

	@field:SerializedName("documents")
	val documents: List<DocumentsItem>,

	@field:SerializedName("nextPageToken")
	val nextPageToken: String
)

data class Fields(

	@field:SerializedName("coordinate")
	val coordinate: Coordinate,

	@field:SerializedName("price")
	val price: Price,

	@field:SerializedName("room_capacity")
	val roomCapacity: RoomCapacity,

	@field:SerializedName("name")
	val name: Name,

	@field:SerializedName("latitude")
	val latitude: Latitude,

	@field:SerializedName("longitude")
	val longitude: Longitude
)

data class Latitude(

	@field:SerializedName("doubleValue")
	val doubleValue: Double
)

data class Name(

	@field:SerializedName("stringValue")
	val stringValue: String
)

data class MapValue(

	@field:SerializedName("fields")
	val fields: Fields
)

data class RoomCapacity(

	@field:SerializedName("integerValue")
	val integerValue: String
)

data class Price(

	@field:SerializedName("integerValue")
	val integerValue: String
)

data class DocumentsItem(

	@field:SerializedName("createTime")
	val createTime: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("updateTime")
	val updateTime: String,

	@field:SerializedName("fields")
	val fields: Fields
)

data class Longitude(

	@field:SerializedName("doubleValue")
	val doubleValue: Double
)

data class Coordinate(

	@field:SerializedName("mapValue")
	val mapValue: MapValue
)
