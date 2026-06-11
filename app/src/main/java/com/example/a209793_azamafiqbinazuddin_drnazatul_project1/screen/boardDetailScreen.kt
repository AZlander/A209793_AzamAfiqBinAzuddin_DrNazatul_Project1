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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.StudyBoard
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.GroveTheme

@Composable
fun BoardDetailScreen(viewModel: GroveViewModel, navController: NavController, boardId: Int) {
    val boards by viewModel.boards.collectAsState()
    val board = boards.find { it.id == boardId }

    BoardDetailContent(
        board = board,
        onBackClick = { navController.popBackStack() }
    )
}

@Composable
fun BoardDetailContent(board: StudyBoard?, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Board Detail", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (board == null) {
            Text("Board not found.")
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(board.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Subject", fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                    Text(board.subject, fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Goal", fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                    Text(
                        if (board.goal.isNotBlank()) board.goal else "No goal set.",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardDetailScreenPreview() {
    GroveTheme {
        BoardDetailContent(
            board = StudyBoard(
                id = 1,
                title = "Android Development",
                subject = "Mobile Computing",
                goal = "Learn Jetpack Compose"
            ),
            onBackClick = {}
        )
    }
}
