package com.empire.custom_savers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.empire.custom_savers.ui.theme.CustomsaversTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomsaversTheme {
                val person = rememberSaveable(saver = PersonSaver.saver) {
                    Person(
                        userName = "Jordan", age = 24, isMarried = false
                    )
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "User name: ${person.userName}")
                    Text(text = "Age: ${person.age}")
                    Text(text = "isMarried: ${person.isMarried}")
                }

            }
        }
    }
}

data class Person(val userName: String, val age: Int, val isMarried: Boolean)

object PersonSaver {
    val saver = mapSaver(save = { person: Person ->
        mapOf(
            "userName" to person.userName, "age" to person.age, "isMarried" to person.isMarried
        )

    }, restore = {
        Person(
            userName = it["userName"] as String,
            age = it["age"] as Int,
            isMarried = it["isMarried"] as Boolean
        )
    })
}