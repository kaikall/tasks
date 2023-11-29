package models

import collection.mutable

/*
object LoginModel{
    private val users = mutable.Map[String, String]("Mark" ->"pass")
    private val publicMessages = mutable.Map[String, String]("sender1" ->"pubmess1")
    private val privMessages = mutable.Map[String, List[String]]("Mark" -> List("message1","message2"))

    def validateUser(username:String, password:String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username:String, password:String):Boolean = {
        if(users.contains(username)) false 
        else{
            users(username)=password
            true
        }
    }

    def getPublicMessages(): mutable.Map[String,String]={
      publicMessages
    }

    def getPrivMessages(username:String): Seq[String]= {
        privMessages.get(username).getOrElse(Nil)
    }

    def addPublicMessage(sender:String, message:String): Unit = {
        publicMessage
    }
    def addPrivMessage(username:String, message:String): Unit = {
        privMessages(username) = message :: privMessages.get(username).getOrElse(Nil)
    }
    def removePublicMessage(username:String, ind :Int): Boolean = {
        if(ind < 0 || publicMessages.get(username).isEmpty || ind >= publicMessages(username).length) false
        else {publicMessages(username) = publicMessages(username).patch(ind, Nil, 1)
        true
        }
    }
    def removePrivateMessage(username:String, ind :Int): Boolean = {
        if(ind < 0 || privMessages.get(username).isEmpty || ind >= privMessages(username).length) false
        else {privMessages(username) = privMessages(username).patch(ind, Nil, 1)
        true
        }
    }
    
    def removeMessage(username:String, ind :Int): Boolean = {
        if(ind < 0 || messages.get(username).isEmpty || ind >= messages(username).length) false
        else {messages(username) = messages(username).patch(ind, Nil, 1)
        true
        }
    }
}

*/
import scala.collection.mutable.ListBuffer

object LoginModel {
    private val users = mutable.Map[String, String]("mlewis" ->"prof", "kaikall" -> "word","web"->"apps")
    private val messages: collection.mutable.Map[String, List[String]] = collection.mutable.Map()

    def validateUser(username:String, password:String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false
    else {
      users += (username -> password)
      // Initialize an empty list for the user's messages
      messages += (username -> List.empty[String])
      true
    }
  }
  def getUsers(): mutable.Map[String,String] = {
    users
  }
  def getMessages(username:String): Seq[String]= {
        messages.get(username).getOrElse(Nil)
    }
  
  def addMessage(username: String, message: String): Unit = {
    if(username=="everyone"){
      for((recipient,_) <- users){
        messages(recipient) = message :: messages.get(recipient).getOrElse(Nil)
      }
    }
    messages(username)= message :: messages.get(username).getOrElse(Nil)
  }

  def removeMessage(username: String, index: Int): Unit = {
    val updatedMessages = messages.getOrElse(username, Nil).zipWithIndex.filterNot { case (_, i) => i == index }.map(_._1)
    messages(username) = updatedMessages
  }
}