package com.modernclient

// cSpell:ignore javafx, initializable, firstname, lastname

import com.modernclient.model.Person
import com.modernclient.model.SampleData
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.SortedList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent

import java.net.URL
import java.util.ResourceBundle
import javafx.beans.value.ObservableValue
import javafx.scene.control.SelectionMode


class FXMLController extends Initializable {


    @FXML
    private var firstnameTextField: TextField = _
    @FXML
    private var lastnameTextField: TextField = _
    @FXML
    private var notesTextArea: TextArea = _
    @FXML
    private var removeButton: Button = _
    @FXML
    private var createButton: Button = _
    @FXML
    private var updateButton: Button = _
    @FXML
    private var listView: ListView[Person] = _

    private val personList: ObservableList[Person] = FXCollections.observableArrayList(Person.extractor)
    // Observable objects returned by extractor (applied to each list element) are listened for changes and
    // transformed into "update" change of ListChangeListener.

    private var selectedPerson: Person = _
    private val modifiedProperty: BooleanProperty = new SimpleBooleanProperty(false)
    private var personChangeListener: ChangeListener[Person] = _


    override def initialize(url: URL, rb: ResourceBundle) = {

        // Disable the Remove/Edit buttons if nothing is selected in the ListView control
        removeButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull())
        updateButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(firstnameTextField.textProperty().isEmpty()
                        .or(lastnameTextField.textProperty().isEmpty())))
        createButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNotNull()
                .or(firstnameTextField.textProperty().isEmpty()
                        .or(lastnameTextField.textProperty().isEmpty())))

        SampleData.fillSampleData(personList)

        // Use a sorted list sort by lastname then by firstname
        val sortedList = SortedList(personList)

        // sort by lastname first, then by firstname ignore notes
        sortedList.setComparator((p1, p2) => {
            var result = p1.getLastname().compareToIgnoreCase(p2.getLastname())
            if (result == 0) {
                result = p1.getFirstname().compareToIgnoreCase(p2.getFirstname())
            }
            result
        })
        listView.setItems(sortedList)

        personChangeListener = (observable: ObservableValue[_ <:Person], oldValue:Person, newValue:Person) => {
            System.out.println("Selected item: " + newValue)
            // newValue can be null if nothing is selected
            selectedPerson = newValue
            modifiedProperty.set(false)
            if (newValue != null) {
                // Populate controls with selected Person
                firstnameTextField.setText(selectedPerson.getFirstname())
                lastnameTextField.setText(selectedPerson.getLastname())
                notesTextArea.setText(selectedPerson.getNotes())
            } else {
                firstnameTextField.setText("")
                lastnameTextField.setText("")
                notesTextArea.setText("")
            }
        }
        listView.getSelectionModel().selectedItemProperty().addListener( personChangeListener )

        // See code above, we cannot assign and pass the parameter at the same time in Scala
        // listView.getSelectionModel().selectedItemProperty().addListener(
        //         personChangeListener = (observable: ObservableValue[_ <:Person], oldValue:Person, newValue:Person) => {
        //             System.out.println("Selected item: " + newValue)
        //             // newValue can be null if nothing is selected
        //             selectedPerson = newValue
        //             modifiedProperty.set(false)
        //             if (newValue != null) {
        //                 // Populate controls with selected Person
        //                 firstnameTextField.setText(selectedPerson.getFirstname())
        //                 lastnameTextField.setText(selectedPerson.getLastname())
        //                 notesTextArea.setText(selectedPerson.getNotes())
        //             } else {
        //                 firstnameTextField.setText("")
        //                 lastnameTextField.setText("")
        //                 notesTextArea.setText("")
        //             }
        //         })

        // Extra from book
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE)
        listView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) => {
                    val selectedItems: ObservableList[Person] = 
                        listView.getSelectionModel().getSelectedItems()
                    // Do something with selectedItems
                    println(selectedItems)
            })

        // Pre-select the first item
        listView.getSelectionModel().selectFirst()

    }

    @FXML
    private def handleKeyAction(keyEvent: KeyEvent) = {
        modifiedProperty.set(true)
    }

    @FXML
    private def createButtonAction(actionEvent: ActionEvent) = {
        System.out.println("Create")
        val person = Person(firstnameTextField.getText(),
                lastnameTextField.getText(), notesTextArea.getText())
        personList.add(person)
        // and select it
        listView.getSelectionModel().select(person)
    }

    @FXML
    private def removeButtonAction(actionEvent: ActionEvent) = {
        System.out.println("Remove " + selectedPerson)
        personList.remove(selectedPerson)
    }

    @FXML
    private def updateButtonAction(actionEvent: ActionEvent) = {
        System.out.println("Update " + selectedPerson)
        val p = listView.getSelectionModel().getSelectedItem()
        listView.getSelectionModel().selectedItemProperty().removeListener(personChangeListener)
        p.setFirstname(firstnameTextField.getText())
        p.setLastname(lastnameTextField.getText())
        p.setNotes(notesTextArea.getText())
        listView.getSelectionModel().selectedItemProperty().addListener(personChangeListener)
        modifiedProperty.set(false)
    }
}