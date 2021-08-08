package org.modernclients.propertiesandbindings

// cSpell:ignore javafx, ints, stackoverflow, nullpointerexception

import collection.JavaConverters._

import javafx.collections.FXCollections
import javafx.collections.ObservableIntegerArray
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.collections.ObservableSet
import javafx.collections.ObservableFloatArray
import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.collections.SetChangeListener
import javafx.collections.ArrayChangeListener
import javafx.beans.binding.Bindings
import java.util.Comparator
import java.util.Random
import java.util.Arrays
import javafx.beans.Observable
import javafx.collections.ListChangeListener.Change
import javafx.collections.MapChangeListener.{Change => MapChange}
import javafx.collections.SetChangeListener.{Change => SetChange}
import java.util.HashMap

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.run
 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
 * ./mill -i --watch modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
 * 
 * Note on resources (see StackOverflow link below): Mill's convention is to 
 * place a resources directory on the lowest level Mill module. To access 
 * these resources one must use the path relative to the application (Mill 
 * module) and not the class (because resources are not copied to the compiled 
 * class directory).
 * 
 * If you want to keep the resources next to the classes, these would require
 * you change Mill behavior to copy them, or do it yourself. 
 * 
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
object ArrayChangeEventExample {

    def ArrayChangeEventExample: Unit = {
        println("\n\nArrayChangeEventExample")

        val ints: ObservableIntegerArray =
                FXCollections.observableIntegerArray(10, 20)

        ints.addListener(
              (array, sizeChanged, from, to) => {
                 val sb = StringBuilder("\tObservable Array = ")
                                 .append(array)
                                 .append("\n")
                                 .append("\t\tsizeChanged = ")
                                 .append(sizeChanged).append("\n")
                                 .append("\t\tfrom = ")
                                 .append(from).append("\n")
                                 .append("\t\tto = ")
                                 .append(to)
                                 .append("\n")
                 println(sb.toString())
             } )

        ints.ensureCapacity(20)

        println("Calling addAll(30, 40):")
        ints.addAll(30, 40)

        val src = Array(50, 60, 70)
        println("Calling addAll(src, 1, 2):")
        ints.addAll(src, 1, 2)

        println("Calling set(0, src, 0, 1):")
        ints.set(0, src, 0, 1)

        println("Calling setAll(src):")
        ints.setAll(src:_*)

        ints.trimToSize()

        val ints2: ObservableIntegerArray =
                FXCollections.observableIntegerArray()
        ints2.resize(ints.size())

        println("Calling copyTo(0, ints2," +
                " 0, ints.size()):")
        ints.copyTo(0, ints2, 0, ints.size())

        println("\tDestination = " + ints2)
    }

    def FXCollectionsExample: Unit = {
        println("\n\nFXCollectionsExample")

        val list : ObservableList[String]         = FXCollections.observableArrayList()
        val map  : ObservableMap[String, String]  = FXCollections.observableHashMap()
        val set  : ObservableSet[Integer]         = FXCollections.observableSet()
        val array: ObservableFloatArray           = FXCollections.observableFloatArray()

        list.addListener( (c => println("\tlist = " + c.getList() )): ListChangeListener[String] )
        //list.addListener( c => println("\tlist = " + c.getList() ))

        map.addListener( (c => println("\tmap = " + c.getMap())): MapChangeListener[String, String] )

        set.addListener( (c => println("\tset = " + c.getSet())): SetChangeListener[Integer] )

        //array.addListener((observableArray, sizeChanged, from, to) => println("\tarray = " + observableArray) )
        array.addListener( ( (observableArray, sizeChanged, from, to) => println("\tarray = " + observableArray)): ArrayChangeListener[ObservableFloatArray] )

        manipulateList(list)
        manipulateMap(map)
        manipulateSet(set)
        manipulateArray(array)
    }

    def manipulateList(list: ObservableList[String]): Unit = {
        println("Calling list.addAll(\"Zero\"," +" \"One\", \"Two\", \"Three\"):")
        list.addAll("Zero", "One", "Two", "Three")

        println("Calling copy(list," +" Arrays.asList(\"Four\", \"Five\")):")
        FXCollections.copy(list, Arrays.asList("Four", "Five"))

        println("Calling replaceAll(list," + " \"Two\", \"Two_1\"):")
        FXCollections.replaceAll(list, "Two", "Two_1")

        println("Calling reverse(list):")
        FXCollections.reverse(list)

        println("Calling rotate(list, 2):")
        FXCollections.rotate(list, 2)

        println("Calling shuffle(list):");
        FXCollections.shuffle(list)

        println("Calling shuffle(list," + " new Random(0L)):")
        FXCollections.shuffle(list, new Random(0L))

        println("Calling sort(list):")
        FXCollections.sort(list)

        println("Calling sort(list, c)" + " with custom comparator: ")
        FXCollections.sort(list, new Comparator[String]() {
                                       override def compare(lhs: String, rhs: String) = {
                                                        // Reverse the order
                                                        rhs.compareTo(lhs) 
                                                    }
                                                }
                            )

        println("Calling fill(list," + " \"Ten\"): ")
        FXCollections.fill(list, "Ten")
    }

    def manipulateMap(map: ObservableMap[String, String]) = {
        println("Calling map.put(\"Key\"," + " \"Value\"):")
        map.put("Key", "Value")
    }

    def manipulateSet(set: ObservableSet[Integer]) = {
        println("Calling set.add(1024):")
        set.add(1024)
    }

    def manipulateArray(array: ObservableFloatArray) = {
        println("Calling  array.addAll(3.14159f," + " 2.71828f):")
        array.addAll(3.14159f, 2.71828f)
    }

    def typeOfChange(change: Change[_ <: String]): String = {
        // just check the first change
        if (change.next) {
            val start = change.getFrom
            val end = change.getTo
            if (change.wasAdded ) s"wasAdded(${change.getAddedSize}): $start - $end"
            // use getPermutation(int i)
            else if (change.wasPermutated) s"wasPermuted: $start - $end"
            else if (change.wasRemoved) s"wasRemoved(${change.getRemovedSize}): $start - $end"
            else if (change.wasReplaced) s"wasReplaced: $start - $end"
            else if (change.wasUpdated) s"wasUpdated: $start - $end"
            else "?" 
        }
        else "no change"
    }

    def ObservableListExample: Unit = {
        println("\n\nFXCollectionsExample")

        val strings: ObservableList[String] = FXCollections.observableArrayList()

        strings.addListener((observable: Observable) => println("\tlist invalidated") )
        strings.addListener((change: Change[_ <: String]) => println(s"\tstrings (${typeOfChange(change)}) = ${change.getList()}") )
        //strings.addListener((change: Change[_ <: String]) => println(s"\tstrings = ${change.getList()}") )

        println("Calling add(\"First\"): ")
        strings.add("First")
        println("Calling add(0, \"Zeroth\"): ")
        strings.add(0, "Zeroth")

        println("Calling addAll(\"Second\"," + " \"Third\"): ")
        strings.addAll("Second", "Third")

        println("Calling set(1," + " \"New First\"): ")
        strings.set(1, "New First")

        val list: java.util.List[String] = Arrays.asList("Second_1", "Second_2")
        println("Calling addAll(3, list): ")
        strings.addAll(3, list)

        println("Calling remove(2, 4): ")
        strings.remove(2, 4)

        val iterator: java.util.Iterator[String] = strings.iterator()
        while (iterator.hasNext()) {
            val next: String = iterator.next()
            if (next.contains("t")) {
                println("Calling remove()" + " on iterator: ")
                iterator.remove()
            }
        }
        println("Calling removeAll(" + "\"Third\", \"Fourth\"): ")
        strings.removeAll("Third", "Fourth")
    }

    def ListChangeEventExample: Unit = {
        println("\n\nListChangeEventExample")

        val strings: ObservableList[String] = FXCollections.observableArrayList()

        strings.addListener(new MyListener())
        println("Calling addAll(\"Zero\"," +
                " \"One\", \"Two\", \"Three\"): ")
        strings.addAll("Zero", "One", "Two", "Three")

        println("Calling" +
                " FXCollections.sort(strings): ")
        FXCollections.sort(strings)

        println("Calling set(1, \"Three_1\"): ")
        strings.set(1, "Three_1")

        println("Calling setAll(\"One_1\"," +
                " \"Three_1\", \"Two_1\", \"Zero_1\"): ")
        strings.setAll("One_1", "Three_1", "Two_1", "Zero_1")

        println("Calling removeAll(\"One_1\"," +
                " \"Two_1\", \"Zero_1\"): ")
        strings.removeAll("One_1", "Two_1", "Zero_1")
    }

    class MyListener extends ListChangeListener[String] {

        override def onChanged(change: Change[_ <: String]) = {
            println("\tlist = " + change.getList())
            println(prettyPrint(change))
        }

        private def prettyPrint(change: Change[_ <: String]): String = {
            val sb: StringBuilder = StringBuilder("\tChange event data:\n")
            var i: Int = 0
            while (change.next()) {
                sb.append(s"\t\tcursor = ${i}\n")
                i += 1
                val kind: String =
                                    if change.wasPermutated() then "permutated"  
                                    else if change.wasReplaced() then "replaced"
                                    else if change.wasRemoved() then "removed" 
                                    else if change.wasAdded() then "added" 
                                    else "none"

                sb.append("\t\tKind of change: ")
                        .append(kind)
                        .append("\n")

                sb.append("\t\tAffected range: [")
                        .append(change.getFrom())
                        .append(", ")
                        .append(change.getTo())
                        .append("]\n")

                if (kind.equals("added") ||
                    kind.equals("replaced")) {

                    sb.append("\t\tAdded size: ")
                        .append(change.getAddedSize())
                        .append("\n")
                    sb.append("\t\tAdded sublist: ")
                        .append(change.getAddedSubList())
                        .append("\n")
                }

                if (kind.equals("removed") ||
                    kind.equals("replaced")) {
                    sb.append("\t\tRemoved size: ")
                        .append(change.getRemovedSize())
                        .append("\n");
                    sb.append("\t\tRemoved: ")
                        .append(change.getRemoved())
                        .append("\n");
                }

                if (kind.equals("permutated")) {
                    val permutationSB: StringBuilder = StringBuilder("[")
                    val from = change.getFrom()
                    val to = change.getTo()

                    val changes = from until to
                    for ( k <- changes ) {
                        val permutation =
                                change.getPermutation(k)
                        permutationSB.append(k)
                                        .append("->")
                                        .append(permutation)
                        if (k < change.getTo() - 1) {
                            permutationSB.append(", ")
                        }
                    }
                    permutationSB.append("]")
                    val permutation = permutationSB.toString()
                    sb.append("\t\tPermutation: ")
                        .append(permutation).append("\n")
                }
            }
            sb.toString()
        }
    }

    def MapChangeEventExample: Unit = {
        println("\n\nMapChangeEventExample")
        
        val map: ObservableMap[String, Integer] = FXCollections.observableHashMap()
        map.addListener(MyMapListener())

        println("Calling put(\"First\", 1): ")
        map.put("First", 1)

        println("Calling put(\"First\", 100): ")
        map.put("First", 100)

        val anotherMap: java.util.Map[String, Integer] = HashMap()
        anotherMap.put("Second", 2)
        anotherMap.put("Third", 3)

        println("Calling putAll(anotherMap): ")
        map.putAll(anotherMap)

        val entryIterator: java.util.Iterator[java.util.Map.Entry[String, Integer]] = map.entrySet().iterator()
        while (entryIterator.hasNext()) {
            val next: java.util.Map.Entry[String, Integer] = entryIterator.next()
            if (next.getKey().equals("Second")) {
                println("Calling remove on entryIterator: ")
                entryIterator.remove()
            }
        }
        val valueIterator: java.util.Iterator[Integer] = map.values().iterator()
        while (valueIterator.hasNext()) {
            val next = valueIterator.next()
            if (next == 3) {
                println("Calling remove on valueIterator: ")
                valueIterator.remove()
            }
        }
    }

    class MyMapListener extends MapChangeListener[String, Integer] {

        override def onChanged(change: MapChange[_ <: String, _ <: Integer]) = {
            println("\tmap = " + change.getMap())
            println(prettyPrint(change))
        }

        private def prettyPrint(change: MapChange[_ <: String, _ <: Integer]): String =  {

            val sb: StringBuilder = StringBuilder("\tChange event data:\n")
            sb.append("\t\tWas added: ")
                    .append(change.wasAdded())
                    .append("\n")
            sb.append("\t\tWas removed: ")
                    .append(change.wasRemoved())
                    .append("\n")
            sb.append("\t\tKey: ")
                    .append(change.getKey())
                    .append("\n")
            sb.append("\t\tValue added: ")
                    .append(change.getValueAdded())
                    .append("\n")
            sb.append("\t\tValue removed: ")
                    .append(change.getValueRemoved())
                    .append("\n")
            sb.toString()
        }
    }

    def SetChangeEventExample: Unit = {
        println("\n\nSetChangeEventExample")

        val set: ObservableSet[String] = FXCollections.observableSet()
        set.addListener(new MySetListener())

        println("Calling add(\"First\"): ")
        set.add("First")
        
        println("Calling addAll(Arrays.asList(\"Second\", \"Third\")): ")
        set.addAll(Arrays.asList("Second", "Third"))

        println("Calling remove(\"Third\"): ")
        set.remove("Third")
    }

    class MySetListener extends SetChangeListener[String] {

        override def onChanged(change: SetChange[_ <: String]) = {
            println("\tset = " + change.getSet())
            println(prettyPrint(change))
        }
        
        private def prettyPrint(change: SetChange[_ <: String]): String = {
            val sb: StringBuilder = StringBuilder("\tChange event data:\n")

            sb.append("\t\tWas added: ")
                    .append(change.wasAdded())
                    .append("\n");
            sb.append("\t\tWas removed: ")
                    .append(change.wasRemoved())
                    .append("\n");
            sb.append("\t\tElement added: ")
                    .append(change.getElementAdded())
                    .append("\n");
            sb.append("\t\tElement removed: ")
                    .append(change.getElementRemoved())
                    .append("\n");
            sb.toString();
        }
    }

    def ArrayChangeEventExampleOriginal: Unit = {
        println("\n\nArrayChangeEventExampleOriginal")

        val ints: ObservableIntegerArray = FXCollections.observableIntegerArray(10, 20)
        ints.addListener( 
            (array, sizeChanged, from, to) => {
                val sb: StringBuilder = StringBuilder("\tObservable Array = ")
                                            .append(array)
                                            .append("\n")
                                            .append("\t\tsizeChanged = ")
                                            .append(sizeChanged).append("\n")
                                            .append("\t\tfrom = ")
                                            .append(from).append("\n")
                                            .append("\t\tto = ")
                                            .append(to)
                                            .append("\n")
                println(sb.toString());
            }
        )
        ints.ensureCapacity(20)

        println("Calling addAll(30, 40):")
        ints.addAll(30, 40)

        val src = Array(50, 60, 70)
        println("Calling addAll(src, 1, 2):")
        ints.addAll(src, 1, 2)

        println("Calling set(0, src, 0, 1):")
        ints.set(0, src, 0, 1)

        println("Calling setAll(src):")
        ints.setAll(src:_*)
        ints.trimToSize()

        val ints2: ObservableIntegerArray = FXCollections.observableIntegerArray()
        ints2.resize(ints.size())

        println("Calling copyTo(0, ints2, 0, ints.size()):")
        ints.copyTo(0, ints2, 0, ints.size())
        println("\tDestination = " + ints2)
    }

    def bindingsUtilityUnidirectional: Unit = {
        println("\n\nbindingsUtilityUnidirectional")

        val jmap: java.util.Map[String, Integer] = java.util.HashMap()
        // this data will be removed upon binding
        jmap.put("a", 1)
        jmap.put("b", 2)
        jmap.put("c", 3)
        jmap.put("d", 4)

        val map: ObservableMap[String, Integer]  = FXCollections.observableHashMap()
        val boundMap = Bindings.bindContent(jmap, map)

        map.addListener(MyMapListener())

        println("Calling put(\"First\", 1): ")
        map.put("First", 1)
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

        println("Calling put(\"First\", 100): ")
        map.put("First", 100)
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

        val anotherMap: java.util.Map[String, Integer] = HashMap()
        anotherMap.put("Second", 2)
        anotherMap.put("Third", 3)

        println("Calling putAll(anotherMap): ")
        map.putAll(anotherMap)
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

        val entryIterator: java.util.Iterator[java.util.Map.Entry[String, Integer]] = map.entrySet().iterator()
        while (entryIterator.hasNext()) {
            val next: java.util.Map.Entry[String, Integer] = entryIterator.next()
            if (next.getKey().equals("Second")) {
                println("Calling remove on entryIterator: ")
                entryIterator.remove()
            }
        }
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

        val valueIterator: java.util.Iterator[Integer] = map.values().iterator()
        while (valueIterator.hasNext()) {
            val next = valueIterator.next()
            if (next == 3) {
                println("Calling remove on valueIterator: ")
                valueIterator.remove()
            }
        }
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

        // Binding is uni-directtonal
        println("""Calling jmap.put("e", 5): """)
        jmap.put("e", 5)
        println(s"jmap = ${jmap.asScala.mkString("{", ",", "}")}")

    }

    def bindingsUtilityBidirectional: Unit = {
        println("\n\nbindingsUtilityBidirectional")

        val map0: ObservableMap[String, Integer]  = FXCollections.observableHashMap()
        // Tgis data will be removed upon binding
        map0.put("a", 1)
        map0.put("b", 2)
        map0.put("c", 3)
        map0.put("d", 4)

        val map: ObservableMap[String, Integer]  = FXCollections.observableHashMap()
        val boundMap = Bindings.bindContentBidirectional(map0, map)

        map.addListener(MyMapListener())

        println("Calling put(\"First\", 1): ")
        map.put("First", 1)
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

        println("Calling put(\"First\", 100): ")
        map.put("First", 100)
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

        val anotherMap: java.util.Map[String, Integer] = HashMap()
        anotherMap.put("Second", 2)
        anotherMap.put("Third", 3)

        println("Calling putAll(anotherMap): ")
        map.putAll(anotherMap)
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

        val entryIterator: java.util.Iterator[java.util.Map.Entry[String, Integer]] = map.entrySet().iterator()
        while (entryIterator.hasNext()) {
            val next: java.util.Map.Entry[String, Integer] = entryIterator.next()
            if (next.getKey().equals("Second")) {
                println("Calling remove on entryIterator: ")
                entryIterator.remove()
            }
        }
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

        val valueIterator: java.util.Iterator[Integer] = map.values().iterator()
        while (valueIterator.hasNext()) {
            val next = valueIterator.next()
            if (next == 3) {
                println("Calling remove on valueIterator: ")
                valueIterator.remove()
            }
        }
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

        // Binding is bi-directional
        println("""Calling jmap.put("e", 5): """)
        map0.put("e", 5)
        println(s"jmap = ${map0.asScala.mkString("{", ",", "}")}")

    }

    def main(args: Array[String]): Unit = {
        ArrayChangeEventExample
        FXCollectionsExample
        ObservableListExample
        ListChangeEventExample
        MapChangeEventExample
        SetChangeEventExample
        ArrayChangeEventExampleOriginal
        bindingsUtilityUnidirectional // experiment
    }
}

