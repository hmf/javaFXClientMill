package com.modernclient.model


import javafx.collections.ObservableList

object SampleData {

    def fillSampleData(backingList: ObservableList[Person]): Unit = {
        backingList.add( Person("Waldo", "Soller", "random notes 1"))
        backingList.add( Person("Herb", "Dinapoli", "random notes 2"))
        backingList.add( Person("Shawanna", "Goehring", "random notes 3"))
        backingList.add( Person("Flossie", "Goehring", "random notes 4"))
        backingList.add( Person("Magdalen", "Meadors", "random notes 5"))
        backingList.add( Person("Marylou", "Berube", "random notes 6"))
        backingList.add( Person("Ethan", "Nieto", "random notes 7"))
        backingList.add( Person("Elli", "Combes", "random notes 8"))
        backingList.add( Person("Andy", "Toupin", "random notes 9"))
        backingList.add( Person("Zenia", "Linwood", "random notes 10"))
    }

    /*
    Glenn Marti
Waldo Soller
Herb Dinapoli
Shawanna Goehring
Flossie Slack
Magdalen Meadors
Marylou Berube
Ethan Nieto
Elli Combes
Andy Toupin
Zenia Linwood
Alan Mckeithan
Kattie Mellott
Benito Kearns
Lloyd Cundiff
Karleen Westrich
Jada Perrotta
Teofila Holbert
Moira Heart
Mitsuko Earwood
     */

}
