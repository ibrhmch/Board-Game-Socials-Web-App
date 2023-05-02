package com.goodboards.app.analyzer

object AnalyzerWorkerHelper {

        fun setDoubleApostrophe(title: String): String {
            var temp = ""
            for(character in title.iterator()){
                if(character == '\''){
                    temp = temp + character.toString() + character.toString()
                }else {
                    temp += character.toString()
                }
            }
            return temp
        }
}