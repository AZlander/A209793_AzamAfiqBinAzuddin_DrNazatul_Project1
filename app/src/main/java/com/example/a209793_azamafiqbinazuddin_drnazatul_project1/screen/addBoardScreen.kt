package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.R
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.AppTheme

@Composable
fun AddBoardScreen(viewModel: GroveViewModel, navController: NavController) {
    AddBoardContent(
        onCreateBoard = { title, subject, goal ->
            viewModel.addBoard(title, subject, goal)
            navController.popBackStack()
        },
        onBack = { navController.popBackStack() }
    )
}

@Composable //ViewModel
fun AddBoardContent(
    onCreateBoard: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var title   by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var goal    by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Back button + Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = stringResource(R.string.new_study_board),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form fields
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.board_title)) },
            placeholder = { Text("e.g. Final Exam Prep") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text(stringResource(R.string.subject)) },
            placeholder = { Text("e.g. Android Development") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Goal") },
            placeholder = { Text("e.g. Finish all labs by Friday") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit button
        Button(
            onClick = {
                if (title.isNotBlank() && subject.isNotBlank()) {
                    onCreateBoard(title, subject, goal)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(R.string.create_board))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBoardScreenPreview() {
    AppTheme {
        AddBoardContent(
            onCreateBoard = { _, _, _ -> },
            onBack = {}
        )
    }
}
