package com.remindermatrix.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.remindermatrix.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TaskCreationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCreateTask() {
        val taskTitle = "My Test Task"

        // 1. Click the FAB to open the AddTaskSheet
        composeTestRule.onNodeWithText("+").performClick()

        // 2. Enter text into the title field
        composeTestRule.onNodeWithLabel("Task Title").performTextInput(taskTitle)

        // 3. Click the "Add Task" button
        composeTestRule.onNodeWithText("Add Task").performClick()

        // 4. Verify that the new task appears in the list
        composeTestRule.onNodeWithText(taskTitle).assertIsDisplayed()
    }
}
