package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This class holds the VM and the Model and will be used to store the course list
 */
class VMM : ViewModel()
{
    // model
    private val data = MutableStateFlow(listOf<Course>())

    val dataReadOnly : StateFlow<List<Course>> = data
    init {
        data.value = data.value + Course("45", "CS", "U Campus")
        data.value = data.value + Course("50", "CS", "U Campus")

    }
    fun addCourse (item: Course) {
        data.value = data.value + item
    }

    fun removeCourse(item: Course) {
        data.value = data.value.filter({ it.id != item.id })
    }
}

/**
 * This is the class for a Course which takes in a department, course number, and a location
 */
class Course(private val courseNumber: String, private val department: String, private val location: String) {
    val id = "$department$courseNumber$location".hashCode()

    fun getCourseName() : String {
        return "$department$courseNumber"
    }

    fun getLocation() : String {
        return location
    }
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                val vm:VMM = viewModel()
                Content(vm)
            }
        }
    }
}

@Composable
fun Content(vm : VMM) {

    // Header
    Column() {
        Text("Courses")
    }
    // Courses
    Column () {
        val courseList by vm.dataReadOnly.collectAsState()
        LazyColumn {
            items(courseList) {
                course ->
                Text(text = "${course.getCourseName()} at ${course.getLocation()}")
                EditCourse(vm, course)
                RemoveCourse(vm, course)
            }
        }
    }
    // Add Course
    var courseNumber by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("")}
    var location by remember { mutableStateOf("")}

    Column() {
        Row () {

            // CourseNumber field
            OutlinedTextField(
                value = courseNumber,
                onValueChange = {
                    // Only allow int inputs
                    if (it.all { it.isDigit()}) {
                    courseNumber = it
                }
                                },
                label = { Text("Course Number")},
            )

            // department field
            OutlinedTextField(
                value = department,
                onValueChange = {
                    department = it
                },
                label = { Text("Department") }
            )

            // location field
            OutlinedTextField(
                value = location,
                onValueChange = {
                    location = it
                },
                label = { Text("Location") }
            )

            // Submit
            Button(
                onClick = {

                    // handle submit
                    val newCourse = Course(courseNumber, department, location)
                    vm.addCourse(newCourse)
                    println(vm.dataReadOnly)
                },
            ) {
                Text("Submit")
            }
        }

    }
}

@Composable
fun EditCourse(vm: VMM, courseToEdit: Course) {
    TODO("Not yet implemented")
}

@Composable
fun RemoveCourse(vm: VMM, courseToRemove: Course) {
    TODO("Not yet implemented")
}