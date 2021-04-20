package com.example.proyectofinal.data

object User{
        private var connexion = false
        private var user = -1

        fun connect(user: Int){
            connexion = true
            this.user = user
        }

        fun disconnect(){
            connexion = false
            this.user = -1
        }

        fun getIdUser(): Int{
           return this.user
        }

        fun getIsConnected(): Boolean{
            return connexion
        }
}