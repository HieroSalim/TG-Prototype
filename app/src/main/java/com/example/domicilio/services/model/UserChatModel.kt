package com.example.domicilio.services.model

class UserChatModel {
    var id: String = ""
    var username: String=""
    var imageURL: String=""

    constructor(id: String, username: String, imageURL: String) {
        this.id = id
        this.username = username
        this.imageURL = imageURL
    }

    constructor()
}