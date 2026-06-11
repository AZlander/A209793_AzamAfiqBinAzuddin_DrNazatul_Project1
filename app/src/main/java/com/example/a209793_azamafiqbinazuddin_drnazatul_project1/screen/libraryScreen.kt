package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.R
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.StudyBoard
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.GroveTheme

@Composable
fun LibraryScreen(viewModel: GroveViewModel, navController: NavController) {
    val boards by viewModel.boards.collectAsState()  // 👈 real data

    LibraryScreenContent(
        boards = boards,
        onNewBoardClick = { navController.navigate("AddBoard") },
        onBoardClick = { boardId -> navController.navigate("BoardDetail/$boardId") }
    )
}

@Composable
fun LibraryScreenContent(
    boards: List<StudyBoard>,
    onNewBoardClick: () -> Unit,
    onBoardClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Library", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            // + button to go to AddBoardScreen
            Button(onClick = onNewBoardClick) {
                Text("+ New")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (boards.isEmpty()) {
            // Empty state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    stringResource(R.string.boarddesc),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(boards) { board ->
                    BoardCard(board = board, onClick = {
                        onBoardClick(board.id)
                    })
                }
            }
        }
    }
}

@Composable
fun BoardCard(board: StudyBoard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(board.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(board.subject, fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
            if (board.goal.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("🎯 ${board.goal}", fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

data class LibraryItem(val title: String, val date: String, val category: String = "Recents")

@Composable
fun LibraryCard(item: LibraryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "List Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.date,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreview() {
    val sampleBoards = listOf(
        StudyBoard(1, "Kotlin Basics", "Programming", "Learn syntax"),
        StudyBoard(2, "Compose UI", "Design", "Master layouts"),
        StudyBoard(3, "Android Architecture", "Development", "Understand MVVM")
    )

    GroveTheme {
        LibraryScreenContent(
            boards = sampleBoards,
            onNewBoardClick = {},
            onBoardClick = {}
        )
    }
}
