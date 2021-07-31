package com.modernclient.model

// cSpell:ignore javafx, firstname, lastname


import javafx.beans.Observable
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.util.Callback

import java.util.Objects

class Person( private var firstname: StringProperty = SimpleStringProperty(this, "firstname", ""), 
              private var lastname: StringProperty = SimpleStringProperty(this, "lastname", ""), 
              private var notes: StringProperty = SimpleStringProperty(this, "notes", "sample notes")) {


    def getFirstname(): String = {
        return firstname.get()
    }

    def firstnameProperty(): StringProperty = {
        return firstname
    }

    def setFirstname(firstname: String) = {
        this.firstname.set(firstname)
    }

    def getLastname(): String = {
        return lastname.get()
    }

    def lastnameProperty(): StringProperty = {
        return lastname
    }

    def setLastname(lastname: String) = {
        this.lastname.set(lastname)
    }

    def getNotes(): String = {
        return notes.get()
    }

    def notesProperty(): StringProperty = {
        return notes
    }

    def setNotes(notes: String) = {
        this.notes.set(notes)
    }

    override def toString(): String = {
        return firstname.get() + " " + lastname.get()
    }

    // override def equals(obj: Any): Boolean = {
    //     if (this == obj) return true
    //     if (obj == null || getClass() != obj.getClass()) return false
    //     val person = obj.asInstanceOf[Person]
    //     return Objects.equals(firstname, person.firstname) &&
    //             Objects.equals(lastname, person.lastname) &&
    //             Objects.equals(notes, person.notes)
    // }

    override def equals(obj: Any): Boolean = {
        obj match {
            case person:Person =>
                Objects.equals(firstname, person.firstname) &&
                Objects.equals(lastname, person.lastname) &&
                Objects.equals(notes, person.notes)
            case _ => 
                false

        }
    }


    override def hashCode(): Int = {
        return Objects.hash(firstname, lastname, notes)
    }

}

object Person {
    def apply(firstname: String, lastname: String, notes: String) = {
        val p = new Person()
        p.setFirstname(firstname)
        p.setLastname(lastname)
        p.setNotes(notes)
        p
    }

    def extractor: Callback[Person, Array[Observable]] = 
        p => Array[Observable](p.lastnameProperty(), p.firstnameProperty())

}
