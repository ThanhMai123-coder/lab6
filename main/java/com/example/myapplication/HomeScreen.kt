package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen() {
    val database = Firebase.database
    val myRef = database.getReference("messages")

    var text by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text(text = "Enter your data") }
        )

        Button(
            onClick = {
                if (text.isNotEmpty()) {
                    myRef.push().setValue(text)
                        .addOnSuccessListener {
                            status = "✅ Dữ liệu đã được lưu!"
                            text = ""
                        }
                        .addOnFailureListener {
                            status = "❌ Lỗi: ${it.message}"
                        }
                } else {
                    status = "⚠️ Vui lòng nhập dữ liệu trước khi gửi"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Submit")
        }

        if (status.isNotEmpty()) {
            Text(text = status, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
